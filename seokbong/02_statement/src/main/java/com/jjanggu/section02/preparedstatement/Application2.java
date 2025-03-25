package com.jjanggu.section02.preparedstatement;

import com.jjanggu.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.jjanggu.common.JDBCTemplate.close;
import static com.jjanggu.common.JDBCTemplate.getConnection;

public class Application2 {

    public static void main(String[] args) {
        // 전체 사원 조회 => ResultSet => List<EmployeeDTO>

        List<EmployeeDTO> empList = new ArrayList<>(); // 최종 결과를 담을 변수 (현재는 텅 빈 상태)

        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        String query = "SELECT * FROM employee ORDER BY salary DESC";

        try {
            pstmt = conn.prepareStatement(query); // 완성형태의 sql문을 담은채로 생성
            rset = pstmt.executeQuery(); // 곧장 실행

            while(rset.next()){
                // 현재 참조한ㄴ 행 => 한 사원 => EmployeeDTO에 담기
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
                // List에 해당 EmployeeDTO 기록
                empList.add(rowEmp);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(pstmt);
            close(conn);
        }

        if(empList.isEmpty()){
            System.out.println("조회된 사원들이 없습니다.");
        }else {
            for(EmployeeDTO emp : empList){
                System.out.println(emp);
            }
        }


    }
}
