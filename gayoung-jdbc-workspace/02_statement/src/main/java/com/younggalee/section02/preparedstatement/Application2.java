package com.younggalee.section02.preparedstatement;

import com.younggalee.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.younggalee.common.JDBCTemplate.close;
import static com.younggalee.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {
        // 전체 사원 조회 >> ResultSet에 여러개의 사원 객체(employeeDTO)를 List형태로 담는다 List<EmployeeDTO>

        List<EmployeeDTO> empList  = new ArrayList<>(); // 최종 결과를 담을 변수 (현재는 텅 빈 상태)



        Connection conn = getConnection(); // 1) db연결

        PreparedStatement pstmt = null;
        ResultSet rset = null;


        String query = "SELECT * FROM employee ";

        try {
            // 2) sql문을 담은 채로 PreparedStatement 생성
            pstmt = conn.prepareStatement(query);

            // 완셩형태의 sql문을 담은채로
            // 3) 곧장실행
            rset = pstmt.executeQuery();

            // 4) 결과 처리
            while(rset.next()) {
                EmployeeDTO emp = new EmployeeDTO();
                emp.setEmpId(rset.getString("emp_id"));
                emp.setEmpName(rset.getString("emp_name"));
                emp.setEmpNo(rset.getString("emp_no"));
                emp.setEmail(rset.getString("email"));

                empList.add(emp);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
            close(conn);
        }

        if (empList.isEmpty()){
            System.out.println("조회된 사원이 없습니다.");
        } else {
            System.out.println("조회된 사원이 있습니다.");
            for (EmployeeDTO emp : empList){
                System.out.println(emp);
            }

        }
    }
}
