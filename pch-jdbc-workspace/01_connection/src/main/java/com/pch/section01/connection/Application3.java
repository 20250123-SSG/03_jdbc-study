package com.pch.section01.connection;

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
            prop.load(new FileReader("src/main/java/com/pch/config/connection-config.properties"));
            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                if(conn != null && !conn.isClosed()){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        /*
            # 정적코딩방식 : db 연결정보를 자바소스코드 내에 명시적으로 작성
            - 문제점
            1) 접속할 DB 연결정보가 변경될 경우 자바소스코드를 수정해야함
             => 수정된 내용을 반영시키고자 할 경우 프로그램을 재구동 시켜야됨
            2) 자바 소스코드를 아는 사람만이 수정 가능
             => 관리자(일반인)은 수정이 어려움

            # 동적코딩방식 : db연결정보를 외부 파일에 작성해 자바 내로 읽어들여 반영시키는 방식
             - 좋은점
             1)
         */
    }
}
