package com.seungjoo.section01.insert;

import com.seungjoo.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.seungjoo.common.JDBCTemplate.close;
import static com.seungjoo.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("메뉴명: ");
        String menuName = sc.nextLine();
        System.out.print("가격: ");
        int menuPrice = sc.nextInt();
        System.out.print("카테고리 번호: ");
        int categoryCode = sc.nextInt();
        sc.nextLine();
        System.out.println("판매여부 결정(y/n)");
        String orderableStatus = sc.nextLine().toUpperCase();


        MenuDTO newMenu = new MenuDTO();
        newMenu.setMenuname(menuName);
        newMenu.setCategoryCode(categoryCode);


        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;

        Properties prop = new Properties(); //키,벨류 문자열일떄 프로러티 사용
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/seungjoo/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();

        }

        String query = prop.getProperty("insertMenu");

        try {
            pstmt= conn.prepareStatement(query);
            pstmt.setString(1, newMenu.getMenuname());
            pstmt.setInt(2, newMenu.getCategoryCode());
            pstmt.setInt(3, newMenu.getMenuPrice());
            pstmt.setString(4, newMenu.getOrderableStatus());

        } catch (SQLException e) {
            e.printStackTrace();

        }finally {
            close(pstmt);
            close(conn);
        }

        if(result > 0){
            System.out.println("메뉴 등록 성공");

        }else{
            System.out.println("메뉴 등록 실패");
        }
    }
}
