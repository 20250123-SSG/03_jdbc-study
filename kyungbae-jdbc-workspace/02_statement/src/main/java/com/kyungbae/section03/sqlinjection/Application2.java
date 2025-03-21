package com.kyungbae.section03.sqlinjection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static com.kyungbae.common.JDBCTemplate.close;
import static com.kyungbae.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {
        // 사번만 가지고만 사원 조회하기 (PreparedStatement 이용)

        Connection conn = getConnection();

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("조회할 사번 : ");
        String empId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id = ?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, empId);

            rset = pstmt.executeQuery();

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
            close(pstmt);
            close(conn);
        }
    }
}
