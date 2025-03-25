package com.inyong.section01.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application2 {
    public static void main(String[] args) {
        String drvier = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost/empdb";
        String user = "inyong";
        String password = "inyong";

        Connection conn = null;

        try {
            Class.forName(drvier);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}
