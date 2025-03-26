package com.inyong.section01.connection;

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
            prop.load(new FileReader("src/main/java/com/inyong/config/connection-config.properties"));

            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);


        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /*
        정적 코딩 방식 : db연결정보를 자바소스코드 내에 명시적으로 작성
        - 문제점
        1) 접속할 db연결정보가 변경될 경우 자바소스코드를 수정해야됨
            => 수정된 내용을 반영시키고자할 경우 프로그램 재구동 시켜야됨
        2) 자바 소스코드를 아닌 사람만이 수정할 수 있음
            => 관리자(일반인)는 수정하기에 어려움

        동적코딩방식 : db연결정보를 외부 파일에 작성하여 자바 내로 읽어들여 반영시키는 방식
        - 좋은점
            1) db연결정보가 변경될 경우 파일만 수정하면 됨
                => 자바 코드를 수정한게 아니므로 프로그램 재구동시킬 필요 없음
            2) .properties와 같이 key=value 형태의 텍스트를 저장하는 파일은 일반인도 수정가능


         */


    }

}
