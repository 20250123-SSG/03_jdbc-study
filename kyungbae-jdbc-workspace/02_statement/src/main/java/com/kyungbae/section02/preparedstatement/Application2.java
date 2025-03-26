package com.kyungbae.section02.preparedstatement;

import com.kyungbae.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.kyungbae.common.JDBCTemplate.close;
import static com.kyungbae.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {

        // 전체 사원 조회 => ResultSet => List<EmployeeDTO>

        List<EmployeeDTO> empList = new ArrayList<>(); // 최종 결과를 담을 변수

        Connection conn = getConnection();

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        String query = "SELECT * FROM employee ORDER BY salary DESC";

        try {
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();

            while (rset.next()){
                EmployeeDTO rowEmp = new EmployeeDTO(
                        rset.getString("emp_id"),
                        rset.getString("emp_name"),
                        rset.getString("emp_no"),
                        rset.getString("email"),
                        rset.getString("phone"),
                        rset.getString("dept_code"),
                        rset.getString("job_code"),
                        rset.getString("sal_level"),
                        rset.getInt("salary"),
                        rset.getDouble("bonus"),
                        rset.getString("manager_id"),
                        rset.getDate("hire_date"),
                        rset.getDate("quit_date"),
                        rset.getString("quit_yn")
                );
                // List 에 해당 EmployeeDTO 기록
                empList.add(rowEmp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
            close(conn);
        }

        if (empList.isEmpty()) {
            System.out.println("비었습니다..");
        } else {
            empList.forEach(System.out::println);
        }

    }
}
