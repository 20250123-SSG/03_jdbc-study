package com.sotogito.section01.statement;

import com.sotogito.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.sotogito.common.JDBCTemplate.close;
import static com.sotogito.common.JDBCTemplate.getConnection;

public class Application3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        EmployeeDTO selectedEmp = null;

        /// 1. db 연결된 Connection객체 필요
        Connection conn = getConnection();

        Statement stmt = null;
        ResultSet rs = null;

        System.out.println("조회하고자하는 사번을 입력하세요.");
        String empId = sc.nextLine();

        String query = String.format("SELECT * FROM employee WHERE emp_id = '%s'", empId);


        ///  2. Statemnet 객체 생성
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()) {
                ///  위 쿼리의 SELECT절에 있는 컬럼만 get 가능함
                System.out.println(rs.getString("emp_id")+ ","+rs.getString("emp_name") + ","+rs.getString("salary"));

                selectedEmp = new EmployeeDTO(); /// 객체로 생성

                /// 조회된 한 행의 모든 컬럼값들을 해당 dto ㄱ개체의 각 필드에 담기 -> mapping
                selectedEmp.setEmpId(rs.getString("emp_id"));
                selectedEmp.setEmpName(rs.getString("emp_name"));
                selectedEmp.setEmpNo(rs.getString("emp_no"));
                selectedEmp.setEmail(rs.getString("email"));
                ///
                selectedEmp.setSalary(rs.getInt("salary"));
                selectedEmp.setBonus(rs.getDouble("bonus"));
                selectedEmp.setHireDate(rs.getDate("hire_date"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            close(rs);
            close(stmt);
            close(conn);
        }

        if(selectedEmp == null) {
            System.out.println("조회된 사원이 업다");
            return;
        }
        System.out.println("조회됨");
        System.out.println(selectedEmp);
    }
}
