package com.inyong.section01.statment;

import com.inyong.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.inyong.common.JDBCTemplate.close;
import static com.inyong.common.JDBCTemplate.getConnection;

public class Application3 {
    public static void main(String[] args) {

        EmployeeDTO selectedEmp = null;


        Connection conn = getConnection();

        ResultSet rset = null;
        Statement stmt = null;

        Scanner sc = new Scanner(System.in);

        System.out.print("조회하고자하는 사번을 입력해주세요 : ");
        String empId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id = '" + empId + "'";
//        System.out.println(query);


            // 2.Statement
        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(query);

            if (rset.next()) {
                System.out.println(rset.getString("emp_id")+ ", " + rset.getString("emp_name")+ ", " + rset.getString("salary"));

                selectedEmp = new EmployeeDTO(); // 조회 결과가 있을경우 생성, 조회된 한 행의 모든 컬럼값들을 해당 dto 객체의 각 필드에 담기

                selectedEmp.setEmpId(rset.getString("emp_id"));
                selectedEmp.setEmpName(rset.getString("emp_name"));
                selectedEmp.setEmpNo(rset.getString("emp_no"));
                selectedEmp.setEmail(rset.getString("email"));
                selectedEmp.setSalary(rset.getInt("salary"));
                selectedEmp.setBonus(rset.getDouble("bonus"));
                selectedEmp.setHireDate(rset.getDate("hire_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            //5 자원반납
            close(rset);
            close(stmt);
            close(conn);
        }

        if (selectedEmp == null) {
            System.out.println("조회된 사원이 없습니다.");

        }else {
            System.out.println("조회된 사원이 있습니다.");
            System.out.println(selectedEmp);
        }


    }
}
