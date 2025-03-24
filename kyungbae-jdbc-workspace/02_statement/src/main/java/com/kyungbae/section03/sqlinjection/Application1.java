package com.kyungbae.section03.sqlinjection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.kyungbae.common.JDBCTemplate.close;
import static com.kyungbae.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        // 사번만 가지고만 사원 조회하기 (Statement 이용)

        Connection conn = getConnection();

        Statement stmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("조회할 사번 : ");
        String empId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id = '" + empId + "'";
        System.out.println(query);

        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(query);

            if (rset.next()) {
                System.out.println("이름 : " + rset.getString("emp_name"));
                System.out.println("부서코드 : " + rset.getString("dept_code"));
                System.out.println("월급 : " + rset.getInt("salary"));
            } else {
                System.out.println("조회 결과가 없습니다.");
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
