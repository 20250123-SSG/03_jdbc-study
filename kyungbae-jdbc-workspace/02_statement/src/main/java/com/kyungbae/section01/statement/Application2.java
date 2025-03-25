package com.kyungbae.section01.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.kyungbae.common.JDBCTemplate.close;
import static com.kyungbae.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {

        // 1. DB와 연결
        Connection conn = getConnection();

        Statement stmt = null;
        ResultSet rset = null;

        String query = "SELECT " +
                            "emp_id, emp_name, salary " +
                        "FROM " +
                            "employee " +
                        "WHERE " +
                            "emp_id = '210'";


        try {
            // 2. statement 객체 생성
            stmt = conn.createStatement();

            // 3. sql문 전달 후 실행 => ResultSet 결과 받기
            rset = stmt.executeQuery(query);

            // 4. 결과 처리 로직 : 1행만 조회될 예정
            if(rset.next()){
                String id = rset.getString("emp_id");
                String name = rset.getString("emp_name");
                int salary = rset.getInt("salary");
                System.out.println(id + ", " + name + ", " + salary);
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
