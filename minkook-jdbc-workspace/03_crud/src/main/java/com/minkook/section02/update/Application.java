package com.minkook.section02.update;

import com.minkook.dto.MenuDto;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.minkook.common.JDBCTemplate.close;
import static com.minkook.common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String [] args){
        //수정할 메뉴 정보 입력
        Scanner sc = new Scanner(System.in);
        System.out.print("변경할 메뉴 번호: ");
        int menuCode = sc.nextInt();
        sc.nextLine();
        System.out.print("변경할 메뉴 명 ");
        String menuName = sc.nextLine();
        System.out.print("변경할 메뉴 가격: ");
        int menuPrice = sc.nextInt();


        //입력값을 전송시키기 위한 DTO에 데이터 담기
        MenuDto menu = new MenuDto(menuCode,menuName,menuPrice);

        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        int result = 0; //처리된 행 수를 받기 위한 변수

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/minkook/mapper/menu-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String query = prop.getProperty("updateMenu");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, menu.getMenuName());
            pstmt.setInt(2,menu.getMenuPrice());
            pstmt.setInt(3,menu.getMenuCode());

            result = pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(pstmt);
            close(conn);
        }

        //update문 진행

        //응답메세지 출력

        if(result > 0 ){
            System.out.println("성공적으로 수정되었습니다.");
        }else {
            System.out.println("수정할 메뉴를 찾지 못했습니다.");
        }

    }
}
