package com.pch.section02.perparedstatement;

import com.pch.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static com.pch.common.JDBCTemplate.close;
import static com.pch.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {

        EmployeeDto empDto = null;

        Connection conn = getConnection();

        PreparedStatement psmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("조회하고자 하는 사번 입력 : ");
        String empId = sc.nextLine();

        String query = "SELECT * from employee WHERE emp_id=?";

        try {
            psmt = conn.prepareStatement(query);
            psmt.setString(1, empId); // 쿼리문 안에 1번째 물음표에 empId 를 채워넣겠다
            rset = psmt.executeQuery();
            while (rset.next()) {
                empDto = new EmployeeDto();
                System.out.println(rset.getInt("emp_id"));
                empDto.setEmp_id(rset.getInt("emp_id"));
                empDto.setEmp_name(rset.getString("emp_name"));
                empDto.setEmp_no(rset.getString("emp_no"));
                empDto.setEmail(rset.getString("email"));
                empDto.setPhone(rset.getString("phone"));
                empDto.setDept_code(rset.getString("dept_code"));
                empDto.setJob_code(rset.getString("job_code"));
                empDto.setSalary(rset.getDouble("salary"));
                empDto.setBonus(rset.getDouble("bonus"));
                empDto.setManager_id(rset.getString("manager_id"));
                empDto.setHire_date(rset.getDate("hire_date"));
                empDto.setQuit_date(rset.getDate("quit_date"));
                empDto.setQuit_yn(rset.getString("quit_yn").charAt(0));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn);
        }

        if(empDto != null) {
            System.out.println(empDto.toString());
        } else {
            System.out.println("조회된 사원이 없습니다.");
        }

    }
}
