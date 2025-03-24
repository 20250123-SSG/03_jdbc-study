package com.ino.section01.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application1 {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/empdb","ino","ino");
            // db url(jdbc:mysql://address:portnum/dbname), userid, passwd
            System.out.println("conn: " + conn);
        } catch (ClassNotFoundException | SQLException e) { // 라이브러리 미등록 or 오타
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
