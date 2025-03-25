package com.younggalee.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {

    // Connection 생성 후 반환
    public static Connection getConnection() {
        Properties prop = new Properties();
        Connection conn = null;

        try {
            prop.load(new FileReader("src/main/java/com/younggalee/config/connection-config.properties"));

            Class.forName(prop.getProperty("driver"));


            conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        }


        return conn;
    }
    // 전달받은 Connection 반납

    public static void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    //ResultSet 전달받아 반납
    public static void close(ResultSet rset) {
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    // Statement 전달받아 반납
    public static void close(Statement stmt) {
        try {
            if ((stmt != null) && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}