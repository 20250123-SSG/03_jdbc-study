package com.kyungbae.section02.preparedstatement;

import com.kyungbae.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static com.kyungbae.common.JDBCTemplate.close;
import static com.kyungbae.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {

        // 사번으로 사원 조회 => 한행 조회 => ResultSet => Employee DTO

        EmployeeDTO emp = null; // 최종결과를 담아낼 변수

        Connection conn = getConnection(); // 1. db 연결

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("사번 입력 : ");
        String empId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id = ?";

        try {
            pstmt = conn.prepareStatement(query); // 2. sql문을 담은 채로 PreparedStatement 생성

            // 현재 담겨있는 미완성된 sql문을 완성형으로 만들기 (사용자가 입력한 값을 ?에 채우기)
            // pstmt.setXXX(홀더순번, 대체할값)
            pstmt.setString(1, empId);

            rset = pstmt.executeQuery(); // 3. sql문 실행 후 결과 받기

            // 4. 결과 처리
            if (rset.next()) {
                emp = new EmployeeDTO();
                emp.setEmpId(rset.getString("emp_id"));
                emp.setEmpName(rset.getString("emp_name"));
                emp.setEmail(rset.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt); // Statement => 다형성 적용
            close(conn);
        }

        if (emp == null) {
            System.out.println("조회결과가 없습니다.");
        } else {
            System.out.println(emp);
        }
    }
}
