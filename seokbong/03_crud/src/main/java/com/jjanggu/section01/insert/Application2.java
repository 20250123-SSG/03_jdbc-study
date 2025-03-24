package com.jjanggu.section01.insert;

import com.jjanggu.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.jjanggu.common.JDBCTemplate.close;
import static com.jjanggu.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {

        // 추가할 메뉴 정보 입력받기
        Scanner sc = new Scanner(System.in);
        System.out.println("메뉴명: ");
        String menuName = sc.nextLine();
        System.out.println("가격: ");
        int menuPrice = sc.nextInt();
        System.out.println("카테고리번호: ");
        int categoryCode = sc.nextInt();
        sc.nextLine();
        System.out.println("판매여부 결정(y/n): ");
        String orderableStatus = sc.nextLine().toUpperCase();

        MenuDTO newMenu = new MenuDTO();
        newMenu.setMenuName(menuName);
        newMenu.setMenuPrice(menuPrice);
        newMenu.setCategoryCode(categoryCode);
        newMenu.setOrderableStatus(orderableStatus);

        // insert문 진행
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        int result = 0; // 최종 결과(처리된 행수)를 기록할 변수

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/jjanggu/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String query = prop.getProperty("insertMenu");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, newMenu.getMenuName());
            pstmt.setInt(2, newMenu.getMenuPrice());
            pstmt.setInt(3, newMenu.getCategoryCode());
            pstmt.setString(4, newMenu.getOrderableStatus());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(pstmt);
            close(conn);
        }

        // 응답 출력
        if(result > 0) {
            System.out.println("메뉴 등록 성공");
        }else{
            System.out.println("메뉴 등록");
        }
    }
}

