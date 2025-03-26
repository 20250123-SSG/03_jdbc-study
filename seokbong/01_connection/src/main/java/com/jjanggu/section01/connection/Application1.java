package com.jjanggu.section01.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application1 {
    public static void main(String[] args) {

        /*
            ## java.sql.Connection ##
            1. DB 접속을 위해 필요한 인스턴스
            2. 접속할 DB의 정보를 제시하여 인스턴스를 생성
               => DriverManager의 정적메소드를 통해 생성
            3. 인스턴스 생성 전 접속할 DB의 Driver를 등록해야됨
         */

        Connection conn = null;

        try {

            // 1) JDBC Driver 등록 (MySQL 전용의 Driver 클래스 등록)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2) db 연결 => Connection 인스턴스 생성 (디비url, 계정명, 비번)
//            conn = DriverManager.getConnection(접속할db의url,접속할계정,계정의비번);
            conn = DriverManager.getConnection("jdbc:mysql://localhost/empdb", "jjanggu", "jjanggu");

            System.out.println("conn: " + conn);

        } catch (ClassNotFoundException e) { // Driver 클래스를 찾지 못할 경우 발생되는 예외 (mysql-connector-j 라이브러리 등록안했거나, 오타있거나)
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
