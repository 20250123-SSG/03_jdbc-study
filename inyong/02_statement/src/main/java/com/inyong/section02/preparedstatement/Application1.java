package com.inyong.section02.preparedstatement;

import com.inyong.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static com.inyong.common.JDBCTemplate.close;
import static com.inyong.common.JDBCTemplate.getConnection;

public class Application1 {
/*
PreparedStatement

statement의 하위
미리 sql문을 담은 상태로 생성
플레이스홀더(?)를 사용해 런타임 중에 매개변수에 값을 전달시킬 수 있음
=> 동적 쿼리(사용자의 입력값에 따라 매번 달라지는 쿼리) 생성에 유용


 */

    public static void main(String[] args) {
// 사번으로 사원 조회 => 한 행 조회 => ResultSet => EmployeeDTO

        EmployeeDTO emp = null;

        Connection conn = getConnection(); // 1) db 연결


        PreparedStatement pstmt = null;
        ResultSet rset = null;


        Scanner sc = new Scanner(System.in);

        System.out.print("조회하고자하는 사번을 입력해주세요 : ");
        String empId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id = ?";

        try {
            pstmt = conn.prepareStatement(query); // sql문 담은채로 preparedStatement 생성

            // 현재 담겨있는 미완성된 sql문

            // pstmt.setXXX
            pstmt.setString(1, empId);

            rset = pstmt.executeQuery(); // sql문 실행 후 결과 받기

            // 결과 처리
            if (rset.next()) {
                emp = new EmployeeDTO();
                emp.setEmpId(rset.getString("emp_id"));
                emp.setEmpName(rset.getString("emp_name"));
                emp.setEmail(rset.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            //5 자원반납
            close(rset);
            close(pstmt);
            close(conn);
        }









    }

}
