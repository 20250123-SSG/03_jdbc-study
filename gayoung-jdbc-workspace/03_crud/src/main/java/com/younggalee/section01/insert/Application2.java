package com.younggalee.section01.insert;

import com.younggalee.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.younggalee.common.JDBCTemplate.close;
import static com.younggalee.common.JDBCTemplate.getConnection;


public class Application2 {
    public static void main(String[] args) {
        // 추가할 메뉴 정보 입력받기
        Scanner sc = new Scanner(System.in);
        System.out.print("메뉴명 : ");
        String menuName = sc.nextLine();
        System.out.print("가격 : ");
        int menuPrice = sc.nextInt();
        System.out.print("카테고리번호 : ");
        int categoryCode = sc.nextInt();
        System.out.print("판매여부 결정(y/n) : ");
        String orderableStatus = sc.nextLine().toUpperCase();

        MenuDTO menu = new MenuDTO();
        menu.setMenuName(menuName);
        menu.setMenuPrice(menuPrice);
        menu.setCategoryCode(categoryCode);
        menu.setOrderableStatus(orderableStatus);

        // insert문 실행
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        int result = 0; // 최종 결과(처리된 행수)를 기록할 변수

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("com/younggalee/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String query = prop.getProperty("insertMenu");
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, menu.getMenuName()); //첫번째 물음표에 string형태의 menu 객체의 name 넣겠다.
            pstmt.setInt(2, menu.getMenuPrice());
            pstmt.setInt(3, menu.getCategoryCode());
            pstmt.setString(4, menu.getOrderableStatus()); //첫번째 물음표에 string형태의 menu 객체의 name 넣겠다.

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(pstmt);
            close(conn);
        }

        //응답 출력
        if (result > 0) {
            System.out.println("메뉴등록성공");
        } else {
            System.out.println("메뉴등록실패");
        }


    }
}
