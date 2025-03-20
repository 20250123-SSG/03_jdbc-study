package com.johnth.section01.statement;

import com.johnth.model_dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.johnth.common.JDBCTemplate.close;
import static com.johnth.common.JDBCTemplate.getConnection;

public class Application3 {
    public static void main(String[] args) {
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        EmployeeDTO selectedEmp = null;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter employee ID: ");
        String empId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id = '" + empId + "'";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            if (rs.next()) {
//                System.out.println("Employee ID: " + rs.getString("emp_id"));

                selectedEmp = new EmployeeDTO();

                // mapping
                selectedEmp.setEmpId(rs.getString("emp_id"));
                selectedEmp.setEmpName(rs.getString("emp_name"));
                selectedEmp.setEmpNo(rs.getString("emp_no"));
                selectedEmp.setSalary(rs.getInt("salary"));
                selectedEmp.setBonus(rs.getDouble("bonus"));
                selectedEmp.setHireDate(rs.getDate("hire_date"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(stmt);
            close(conn);
        }

        if(selectedEmp != null) {
            System.out.println("Employee status: " + selectedEmp);
        }else{
            System.out.println("Employee ID not found");
        }

    }
}
