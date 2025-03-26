package com.kyungbae.section01.connection;


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
            // 저장한 외부 properties 파일 불러오기
            prop.load(new FileReader("src/main/java/com/kyungbae/config/connection-config.properties"));

            // 외부 properties 값의 value 대입
            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String pwd = prop.getProperty("pwd");

            Class.forName(driver); // jdbc driver 등록
            conn = DriverManager.getConnection(url, user, pwd);
            // db 연결

            System.out.println(conn);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
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
