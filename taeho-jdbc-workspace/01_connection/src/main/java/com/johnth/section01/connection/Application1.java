package com.johnth.section.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
    ##java.sql.Connection ##
    1. DB접속을 위해 필요한 인스턴스
    2. 접속할 DB의 정보를 제시하여 인스턴스를 생성
        => DriverManager의 정적메서드를 통해 생성
    3. 인스턴스 생성 전 접속할 DB의 Driver를 등록해야됨
*/

public class Application1 {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            // 1) JDBC Driver 등록(Mysql 전용의 Driver클래스 등록)
             Class.forName("com.mysql.cj.jdbc.Driver");

             // 2) db 연결 => Connection 인스턴스 생성
//            conn = DriverManager.getConnection("jdbc:mysql://{IP}:{PORT}",{User_Name},{User_Pwd});
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/empdb","TH","root");

            System.out.println("conn: " + conn);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
