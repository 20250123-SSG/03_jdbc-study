package com.younggalee.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {
    public static Connection getConnection(){
        Properties prop = new Properties();
        try {
            prop.load(new FileReader("src/main/java/com/younggalee/config/connection-config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String driver = prop.getProperty("driver");
        String url = prop.getProperty("url");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");

        Connection conn = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false); // autoCommit 비활성화
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void close(Connection conn){
        try{
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet rset){
        try{
            if(rset != null && !rset.isClosed()){
                rset.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement stmt){ // 이게 PreparedState 의 부모타입이라 하나 만들어두면 prepar~ 에서도 사용할 수 있다.
        try{
            if(stmt != null && !stmt.isClosed()){
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TODO : commit, rollback
    public static void commit(Connection conn){
        try {
            if(conn != null && !conn.isClosed()){
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback(Connection conn){
        try {
            if(conn != null && !conn.isClosed()){
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
