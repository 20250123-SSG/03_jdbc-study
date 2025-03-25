package com.kyungbae.section01.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application2 {
    public static void main(String[] args) {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost/empdb";
        String user = "kyungbae";
        String pwd = "kyungbae";

        Connection conn = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pwd);

            System.out.println(conn);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
