package com.jjanggu.section01.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.jjanggu.common.JDBCTemplate.close;
import static com.jjanggu.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {

        // 1. Connection 객체 생성
        Connection conn = getConnection();

        Statement stmt = null;
        ResultSet rset = null;

        String query = "SELECT emp_id, emp_name, salary FROM employee WHERE emp_id = '210'";

        try {
            // 2. Statement 객체 생성
            stmt = conn.createStatement();

            // 3. sql문 전달 후 실행 => 결과 받기
            rset = stmt.executeQuery(query);

            //4. 결과 처리 로직
            if(rset.next()){
                System.out.println(rset.getString("emp_id") + ", " + rset.getString("emp-name") + ", " + rset.getString("salary"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            close(rset);
            close(stmt);
            close(conn);
        }
    }
}
