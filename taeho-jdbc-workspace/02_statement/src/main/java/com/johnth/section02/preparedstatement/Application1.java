package com.johnth.section02.preparedstatement;

import com.johnth.model_dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static com.johnth.common.JDBCTemplate.close;
import static com.johnth.common.JDBCTemplate.getConnection;

/*
    ## PreparedStatement
    - Statement의 하위 인터페이스
    - 미리 SQL을 담은 상태로 생성
    - PlaceHolder(<T>)를 사용해 런타임 중에 매개변수 값을 전달시킬 수 있음
        => 동적 쿼리 생성에 유용
*/
public class Application1 {
    public static void main(String[] args) {
        EmployeeDTO emp = null;
        Connection conn = getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter employee ID: ");
        String empId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, empId);

            rs = ps.executeQuery();

            if (rs.next()) {
                emp = new EmployeeDTO();
                emp.setEmpId(rs.getString("emp_id"));
                emp.setEmpName(rs.getString("emp_name"));
                emp.setSalary(rs.getInt("salary"));
                emp.setBonus(rs.getDouble("bonus"));
                emp.setDeptCode(rs.getString("dept_code"));
                emp.setJobCode(rs.getString("job_code"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }

        System.out.println(emp.toString());


    }
}
