package com.johnth.section02.preparedstatement;

import com.johnth.model_dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.johnth.common.JDBCTemplate.close;
import static com.johnth.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {
        List<EmployeeDTO> empList = new ArrayList<EmployeeDTO>();

        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM employee";

        try {
            ps = conn.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                EmployeeDTO emp = new EmployeeDTO(
                        rs.getString("emp_id"),
                        rs.getString("emp_name"),
                        rs.getString("emp_no"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("dept_code"),
                        rs.getString("job_code"),
                        rs.getString("sal_level"),
                        rs.getInt("salary"),
                        rs.getDouble("bonus"),
                        rs.getString("manager_id"),
                        rs.getDate("hire_date"),
                        rs.getDate("quit_date"),
                        rs.getString("quit_yn")
                );
                empList.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }

        if (!empList.isEmpty()) {
            for (EmployeeDTO emp : empList) {
                System.out.println(emp);
            }
        } else {
            System.out.println("No employee found");
        }
    }
}
