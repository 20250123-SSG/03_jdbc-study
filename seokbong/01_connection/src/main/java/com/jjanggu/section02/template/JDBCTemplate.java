package com.jjanggu.section02.template;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTemplate {

    // db 연결 후 연결된 Connection 객체를 반환
    public static Connection getConnection() {

        Properties prop = new Properties();
        Connection conn = null;

        try {
            prop.load(new FileReader("src/main/java/com/jjanggu/config/connection-config.properties"));

            Class.forName(prop.getProperty("driver"));
            conn = DriverManager.getConnection(prop.getProperty("url")
                                             , prop.getProperty("user")
                                             , prop.getProperty("password"));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;

    }

    // 반납할 Connection 객체 전달받아서 자원 반납
    public static void close(Connection conn){
        try {
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
