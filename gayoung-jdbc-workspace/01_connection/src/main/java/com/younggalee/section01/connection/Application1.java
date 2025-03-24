package com.younggalee.section01.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application1 {

    public static void main(String[] args) {

    /*
    java.sql.Connection

    1. DB 접속을 위해 필요한 인스턴스
    2. 접속할 DB의 정보를 제시하여 인스턴스를 생성
        => DriverManger의 정적메소드를 통해생성
    3. 인스턴스 생성 전 접속할 DB의 Driver를 등록해야됨

     */
        Connection conn = null;


        try {
            // 1) JDBC Driver 등록 (Mysql 전용 Driver 클래스 등록)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2) 연결
            // conn = DriverManager.getConnection(접속할db의url, 접속할계정, 계정의 비번);
            conn = DriverManager.getConnection("jdbc:mysql://localhost/empdb", "YoungGaLee", "younggalee");
            System.out.println("con: " + conn);
        } catch (ClassNotFoundException | SQLException e) { // 클래스를 찾지 못할경우 발생되는 예외 (오타 또는 라이브러리 없음)
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }
}