package com.younggalee.section01.statement;

import com.younggalee.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.younggalee.common.JDBCTemplate.close;
import static com.younggalee.common.JDBCTemplate.getConnection;

public class Application3 {
    public static void main(String[] args){

        // 최종 결과를 담을 변수
        EmployeeDTO selectedEmp = null;


        // 1. db 연결된 Connection 객체 필요
        Connection conn = getConnection();

        Statement stmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("조회하고자하는 사번을 입력해주세요 : ");
        String empId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id = '" + empId + "'";

        System.out.println(query);

        try{
            // 2. Statement 객체 생성
            stmt = conn.createStatement();
            //3. sql문 실행 후 결과 받기
            rset = stmt.executeQuery(query);
            // 4. 결과 로직 처리
            if (rset.next()){
                //반환불가 System.out.println(rset.getString("emp_id") + ", " + rset.getString("emp_name") + ", " +rset.getString("salary"));

                selectedEmp = new EmployeeDTO(); // 조회결과가 있을 경우 생성이 진행된다.

                // 조회된 한 행의 모든 컬럼값을 DTO 객체의 각 필드에 담기
                selectedEmp.setEmpId(rset.getString("emp_id"));
                selectedEmp.setEmpName(rset.getString("emp_name"));
//                selectedEmp.setEmpNo(rset.getString("EMP_NO"));
                selectedEmp.setEmail(rset.getString("email"));
                // ...
                selectedEmp.setSalary(rset.getInt("salary")); // 타입 맞추기
                selectedEmp.setBonus(rset.getDouble("bonus")); // 타입 맞추기
                selectedEmp.setHireDate(rset.getDate("hire_date")); // 타입 맞추기

            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            // 자원반납
            close(rset);
            close(stmt);
            close(conn);
        }

        if (selectedEmp == null){
            System.out.println("조회된 사원이 없습니다.");
        } else {
            System.out.println("조회된 사원이 있습니다.");
            System.out.println(selectedEmp);
        }
    }
}
