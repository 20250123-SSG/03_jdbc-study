package com.johnth.section02.preparedstatement;

import com.johnth.model_dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static com.johnth.common.JDBCTemplate.close;
import static com.johnth.common.JDBCTemplate.getConnection;

public class Application3 {
    public static void main(String[] args) {
        List<EmployeeDTO> empList = new ArrayList<EmployeeDTO>();

/*
        properties를 사용하지 않고 코드내에 쿼리를 입력하는 방법
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("조회할 성 입력: ");
        String firstName = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_name LIKE CONCAT(?, '%')";

 */
        Properties prop = new Properties();
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("조회할 성 입력: ");
        String firstName = sc.nextLine();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/johnth/mapper/employee_query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String query = prop.getProperty("selectEmpByFirstName");


        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, firstName);

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
