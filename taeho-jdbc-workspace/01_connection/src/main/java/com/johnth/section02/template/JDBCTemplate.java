package com.johnth.section02.template;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTemplate {
    public static Connection getConnection() {

        Properties props = new Properties();
        Connection conn = null;

        try{
            props.load(new FileReader("src/main/java/com/johnth/config/connection-config.properties"));

            Class.forName(props.getProperty("driver"));
            conn = DriverManager.getConnection(props.getProperty("url")
                                                ,props.getProperty("username")
                                                ,props.getProperty("password"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 자원 반납 메서드
    public static void close(Connection conn) {
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
