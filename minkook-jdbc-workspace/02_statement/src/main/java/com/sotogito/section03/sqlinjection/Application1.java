package com.sotogito.section03.sqlinjection;

import com.sotogito.common.JDBCTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.sotogito.common.JDBCTemplate.getConnection;

public class Application1 {

    public static void main(String[] args) {
        /// 사번만을 가지고 사원 조회하기 - Statement이용


        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("조회할 사번 : ");
        String inputEmpId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id = '" + inputEmpId + "'";
        System.out.println(query);


        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                System.out.println(rs.getString("emp_name") + "," + rs.getString("job_code") + "," + rs.getString("salary"));
            }else {
                System.out.println("조회된 사원이 없습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCTemplate.close(rs);
            JDBCTemplate.close(stmt);
            JDBCTemplate.close(conn);
        }

        /**
         * ## SQL Injection
         * 1. 애플리케이션과 데이터베이스 간의 연동에서 발생될 수 있는 취약점
         * 2. 공격자가 악의적으로 조작된 쿼리를 삽입해 데이터베이스 정보를 불법적으로 조적할 수 있는 공격 기법
         * 3. 사용자 입력값에 SQL문을 직접 삽입해 query를 완성시켜 조회되도록함
         *
         */
    }
}
