package com.jjanggu.section01.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application2 {
    public static void main(String[] args) {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost/empdb)";
        String user = "jjanggu";
        String password = "jjanggu";

        Connection conn = null;


        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
