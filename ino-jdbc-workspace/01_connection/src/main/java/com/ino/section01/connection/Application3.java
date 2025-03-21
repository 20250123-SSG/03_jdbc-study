package com.ino.section01.connection;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Application3 {
    public static void main(String[] args) {
        Properties prop = new Properties();

        Connection conn = null;

        try {
            prop.load(new FileReader("src/main/java/com/ino/config/connector-config.properties"));
            String dirverName = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            Class.forName(dirverName);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("conn : " + conn);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
