package com.younggalee.section02.update;
import com.younggalee.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.younggalee.common.JDBCTemplate.close;
import static com.younggalee.common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) throws FileNotFoundException {
        //수정할 메뉴 정보 입력
        Scanner sc = new Scanner(System.in);
        System.out.print("변경할 메뉴 번호: ");
        int menuCode = sc.nextInt();
        sc.nextLine();
        System.out.print("변경할 메뉴명 : ");
        String menuName = sc.nextLine();
        System.out.print("변경할 메뉴 가격 : ");
        int menuPrice = sc.nextInt();

        //입력값을 전송시키기 위한 DTO에 데이터 담기
        MenuDTO menu = new MenuDTO(menuCode, menuName, menuPrice);
        //update문 진행
        Connection conn =getConnection();
        PreparedStatement pstmt = null;
        int result = 0;

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/younggalee/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String query = prop.getProperty("updateMenu");
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, menu.getMenuName());
            pstmt.setInt(2, menu.getMenuPrice());
            pstmt.setInt(3, menu.getMenuCode());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }


        //응답메세지 출력
        if (result > 0){
            System.out.println("성공적으로 수정되었습니다.");
        } else {
            System.out.println("수정할 메뉴를 찾지 못했습니다.");
        }
    }
}
