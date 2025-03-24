package com.ino.section03.delete;

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
        Connection conn = getConnection();
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstmt = null;
        int result = 0;
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ino/mapper/menu-query.xml"));
            String query = prop.getProperty("deleteMenu");
            pstmt = conn.prepareStatement(query);
            System.out.print("input menuCode: ");
            int menuCode = sc.nextInt();
            pstmt.setInt(1, menuCode);
            result = pstmt.executeUpdate();
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
