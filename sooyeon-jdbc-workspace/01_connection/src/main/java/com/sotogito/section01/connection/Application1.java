package com.sotogito.section01.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application1 {
    public static void main(String[] args) {


        /**
         * ## java.sql.Connection
         * 1. DB 접속을 위해 필요한 인스턴스
         * 2. 접속한 DB의 정보를 제시하여 인스턴스를 생성
         *      => DriverManager의 정적메서드를 통해 생성 (싱글톤 객체 호출)
         * 3. 인스턴스 생성 정 접속할 db에 Driver를 등록해야됨
         */

        Connection conn = null;


        try {
            /// 1) JDBC Driver 등록 (MySQL 전용의 Driver 클래스 등록)
            Class.forName("com.mysql.cj.jdbc.Driver");

            /// 2) db에 연결 -> Connection 인스턴스 생성
            conn = DriverManager.getConnection("jdbc:mysql://localhost/empdb", "sotogito", "sotogito");

            System.out.println("conn " + conn);
        } catch (ClassNotFoundException | SQLException e) { /// Driver 클래스를 찾지 못할 경우의 예외
            e.printStackTrace();
        }finally {
            try {
                if(conn != null)  conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
