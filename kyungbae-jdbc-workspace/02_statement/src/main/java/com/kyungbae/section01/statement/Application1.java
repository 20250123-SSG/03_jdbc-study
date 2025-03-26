package com.kyungbae.section01.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.kyungbae.common.JDBCTemplate.close;
import static com.kyungbae.common.JDBCTemplate.getConnection;


public class Application1 {
    public static void main(String[] args) {

     // java.sql.Statement
        Connection conn = getConnection();
        Statement stmt = null; // 쿼리문 실행 후 결과 받는 객체
        ResultSet rset = null; // executeQury 실행시 결과 반환을 위해

        String query = "SELECT emp_id, emp_name FROM employee";


        try {
            stmt = conn.createStatement(); // statement 객체 생성
            rset = stmt.executeQuery(query); // sql문 실행 및 결과받기

            // 결과 처리 로직
            while (rset.next()){
                System.out.println(rset.getInt("emp_id") + ", " + rset.getString("emp_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 생성의 역순으로 자원 반납
            close(rset);
            close(stmt);
            close(conn);
        }


    }
}
