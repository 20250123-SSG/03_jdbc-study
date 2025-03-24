package com.johnth.section01.insert;

import com.johnth.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.johnth.common.JDBCTemplate.close;
import static com.johnth.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("메뉴명: ");
        String menuName = sc.nextLine();
        System.out.print("가격: ");
        int menuPrice = sc.nextInt();
        System.out.print("카테고리 번호: ");
        int category = sc.nextInt();
        sc.nextLine();
        System.out.print("판매여부(Y/N): ");
        String orderable = sc.nextLine().toUpperCase();

        MenuDTO newMenu = new MenuDTO();

        Connection conn = getConnection();
        PreparedStatement ps = null;
        int result = 0;

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/johnth/mapper/menu_query.xml"));
        } catch (IOException e){
            e.printStackTrace();
        }

        String query = prop.getProperty("insertMenu");

        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, menuName);
            ps.setInt(2, menuPrice);
            ps.setInt(3, category);
            ps.setString(4, orderable);

            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        
        if (result == 1) {
            System.out.println("메뉴 등록 성공");
        } else{
            System.out.println("메뉴 등록 실패");
        }

    }
}