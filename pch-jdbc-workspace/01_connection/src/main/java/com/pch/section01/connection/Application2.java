package com.pch.section01.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application2 {
    public static void main(String[] args) {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/empdb";
        String user = "pch";
        String password = "pch";

        Connection conn = null;

        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
