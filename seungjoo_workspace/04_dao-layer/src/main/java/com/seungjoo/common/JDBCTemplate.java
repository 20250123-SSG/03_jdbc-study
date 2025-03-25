package com.seungjoo.common;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {
    // DB 연결 후 Connection 객체를 반환
    public static Connection getConnection() {
        Properties prop = new Properties();
        Connection conn = null;

        try {
            prop.load(new FileReader("src/main/java/com/seungjoo/config/connection-config.properties"));

            Class.forName(prop.getProperty("driver"));
            conn = DriverManager.getConnection(prop.getProperty("url")
                    , prop.getProperty("user")
                    , prop.getProperty("password"));

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // Connection 객체 닫기
    public static void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("데이터베이스 연결이 닫혔습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ResultSet 객체 닫기
    public static void close(ResultSet rset) {
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
                System.out.println("ResultSet이 닫혔습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Statement 객체 닫기
    public static void close(Statement stmt) {
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
                System.out.println("Statement가 닫혔습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

