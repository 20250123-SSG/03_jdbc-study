package com.kyungbae.section01.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application1 {
    public static void main(String[] args) {
        // java.sql.Connection

        Connection conn = null;

        try {
            // 1. jdbc driver 등록
            Class.forName("com.mysql.cj.jdbc.Driver");
            // className을 못찾았을 경우 class not found exception 발생 가능성 있음
            // 따라서 try.catch 문 작성

            // 2. db 연결 => Connection 인스턴스 생성
            conn = DriverManager.getConnection(
                    /*접속할db의 url*/"jdbc:mysql://localhost/empdb",
                    /*접속할 계정*/ "kyungbae",
                    /*계정의 비번*/ "kyungbae"
            );

            System.out.println("conn: " + conn);


        } catch (ClassNotFoundException e) { // Driver 클래스를 찾지 못할 경우 예외
            e.printStackTrace();
            // mysql-connector-j 라이브러리 등록안했거나, 오타있거나
        } catch (SQLException e) {
            e.printStackTrace();
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
