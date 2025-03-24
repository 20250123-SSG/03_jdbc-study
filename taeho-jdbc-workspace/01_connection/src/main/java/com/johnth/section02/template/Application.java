package com.johnth.section02.template;

import java.sql.Connection;
import java.sql.SQLException;

import static com.johnth.section02.template.JDBCTemplate.close;
import static com.johnth.section02.template.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {

//        Connection conn = JDBCTemplate.getConnection();
        Connection conn = getConnection();
        System.out.println(conn);
        System.out.println("연결완");

        /* JDBC 템플릿에 저장
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
         */
        close(conn);
        System.out.println("반납완");
    }
}
