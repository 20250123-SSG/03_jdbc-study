package com.inyong.section01.statment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.inyong.common.JDBCTemplate.close;
import static com.inyong.common.JDBCTemplate.getConnection;

public class Application2 {

    public static void main(String[] args) {

        // 1. Connection 객체 생성

        Connection conn = getConnection();

        Statement stmt = null;
        ResultSet rset = null;


        String query = "SELECT emp_id, emp_name, salary FROM employee WHERE emp_id = '201'";

        try {
            // 2. Statement 객체  생성
            stmt = conn.createStatement();

            // 3. sql문 전달 후 실행 => 결과 받기
            rset = stmt.executeQuery(query);


            // 4. 결과 처리 로직 (rset으로 부터 조회된 컬럼값 꺼내기)
            if (rset.next()) {
                System.out.println(rset.getString("emp_id")
                        + ", " + rset.getString("emp_name")
                        + ", " + rset.getInt("salary")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 다쓴 자원 반납
            close(rset);
            close(stmt);
            close(conn);

        }
    }
}
