package com.johnth.section01.connection;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
    ## 정적코딩방식: db연결 정보를 자바 소스 코드내에 명시적으로 작성
        - 문제점
            1) 접속할 db연결정보가 변경될 경우 자바소스코드를 수정해야됨
                => 수정된 내용을 반영시키고자 할 경우 프로그램을 재구동 시켜야됨
            2) 자바코드를 아는 사람만이 수정할 수 있음
                => 관리자(일반인)는 수정하기에 어려움
            3) 보안에 취약

    ## 동적코딩방식: db연결 정보를 외부 파일에 작성하여 자바 내로 읽어들여 반영시키는 방식
        - 장점
            1) db연결정보가 변경될 경우 파일만 수정하면됨
                => 자바 코드를 수정한 게 아니므로 프로그램을 재구동 시킬 필요가 없음
            2) .properties 와 key=value 형태의 텍스트로 일반인도 이해하기 쉬움
            3) 설정 파일만 관리하면 되므로 보안성이 정적코딩방식에 비해 뛰어남
 */


public class Application3 {
    public static void main(String[] args) {

        Properties props = new Properties();

        Connection conn = null;
        try {
            props.load(new FileReader("src/main/java/com/johnth/config/connection-config.properties"));

            String driver = props.getProperty("driver");
            String url = props.getProperty("url");
            String username = props.getProperty("username");
            String password = props.getProperty("password");

            Class.forName(driver);

            conn = DriverManager.getConnection(url, username, password);
            System.out.println("conn: " + conn);

        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
