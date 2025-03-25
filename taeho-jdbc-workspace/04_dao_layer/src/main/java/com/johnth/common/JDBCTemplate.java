package com.johnth.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {

    public static Connection getConnection(){

        Properties prop = new Properties();
        try {
            prop.load(new FileReader("src/main/java/com/johnth/config/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String driver = prop.getProperty("driver");
        String url = prop.getProperty("url");
        String user = prop.getProperty("username");
        String password = prop.getProperty("password");

        Connection conn = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void close(Connection conn){
        try{
            if(conn != null && !conn.isClosed())
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet rset){
        try{
            if(rset != null && !rset.isClosed())
                rset.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement stmt){
        try{
            if(stmt != null && !stmt.isClosed())
                stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
