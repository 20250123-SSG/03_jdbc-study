package com.podoseee.section02.template;

import java.sql.Connection;
import java.sql.SQLException;

import static com.podoseee.section02.template.JDBCTemplate.close;
import static com.podoseee.section02.template.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {

        // Connection conn = JDBCTemplate.getConnection();
        Connection conn = getConnection();
        System.out.println(conn);
        System.out.println("sql문 실행완료");

        /*
        try{
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
         */
        close(conn);

    }
}