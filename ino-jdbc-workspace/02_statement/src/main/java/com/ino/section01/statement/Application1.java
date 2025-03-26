package com.ino.section01.statement;

import com.ino.common.JDBCTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.ino.common.JDBCTemplate.close;

public class Application1 {
    public static void main(String[] args) {
        Connection conn = JDBCTemplate.getConnection();

        Statement stmt = null; // 쿼리문 실행 후 결과 받는 객체

        ResultSet rset = null; // select 문 실행 결과를 담는 객체

        String query = "SELECT emp_id, emp_name FROM employee";
        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(query);
            while(rset.next()){
                System.out.println(rset.getString("emp_id") + ", " + rset.getString("emp_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(stmt);
            close(conn);
        }
    }
}
