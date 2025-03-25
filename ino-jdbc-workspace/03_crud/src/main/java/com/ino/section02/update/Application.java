package com.ino.section02.update;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ino.common.JDBCTemplate.close;
import static com.ino.common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("변경 메뉴 번호 : ");
        int menuCode = sc.nextInt();
        sc.nextLine();
        System.out.print("변경 메뉴 명: ");
        String menuName = sc.nextLine();
        System.out.print("변경 메뉴 가격: ");
        int menuPrice = sc.nextInt();
        PreparedStatement pstmt = null;
        Connection conn = getConnection();
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ino/mapper/menu-query.xml"));
            String query = prop.getProperty("updateMenu");
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, menuName);
            pstmt.setInt(2, menuPrice);
            pstmt.setInt(3, menuCode);
            System.out.println(pstmt.executeUpdate());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }
    }
}
