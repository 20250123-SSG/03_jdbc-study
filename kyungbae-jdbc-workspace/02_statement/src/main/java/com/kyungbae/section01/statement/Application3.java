package com.kyungbae.section01.statement;

import com.kyungbae.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.kyungbae.common.JDBCTemplate.close;
import static com.kyungbae.common.JDBCTemplate.getConnection;

public class Application3 {
    public static void main(String[] args) {

        // 최종 결과를 담을 변후
        EmployeeDTO selectedEmp = null;

        // 1. db 연결된 Connection 객체
        Connection conn = getConnection();

        Statement stmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);

        System.out.print("조회할 사번 : ");
        String empId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id = '" + empId + "'";
        // System.out.println(query); // 구문 테스트

        try {
            // 2. Statement 객체 생성
            stmt = conn.createStatement();
            // 3. sql문 실행 후 결과 받기
            rset = stmt.executeQuery(query);

            // 4. 결과 로직 처리
            if (rset.next()) {
                /*System.out.println(rset.getString("emp_id") + ", " +
                        rset.getString("emp_name") + ", " +
                        rset.getInt("salary"));*/
                selectedEmp = new EmployeeDTO(); // 조회결과가 있을 경우 생성진행

                // 조회된 항 행의 모든 컬럼값들을 해당 dto 객체의 각 필드에 담기 => Mapping
                selectedEmp.setEmpId(rset.getString("emp_id"));
                selectedEmp.setEmpName(rset.getString("emp_name"));
                selectedEmp.setEmpNo(rset.getString("emp_no"));
                selectedEmp.setEmail(rset.getString("email"));
                selectedEmp.setPhone(rset.getString("phone"));
                selectedEmp.setDeptCode(rset.getString("dept_code"));
                selectedEmp.setJobCode(rset.getString("job_code"));
                selectedEmp.setSalLevel(rset.getString("sal_level"));
                selectedEmp.setSalary(rset.getInt("salary"));
                selectedEmp.setBonus(rset.getDouble("bonus"));
                selectedEmp.setHireDate(rset.getDate("hire_date"));
                selectedEmp.setQuitDate(rset.getDate("quit_date"));
                selectedEmp.setQuitYn(rset.getString("quit_yn"));


            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(stmt);
            close(conn);
        }

        if (selectedEmp == null) {
            System.out.println("조회된 사원이 없습니다.");
        } else{
            System.out.println(selectedEmp);
        }
    }
}
