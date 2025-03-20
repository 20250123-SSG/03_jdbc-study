package com.johnth.section03.sqlinjection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.johnth.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Employee ID: ");
        String empId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id = '" + empId + "'";
        System.out.println(query);

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("emp_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
