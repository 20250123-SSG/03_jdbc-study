package com.jjanggu.section01.statement;

import com.jjanggu.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.jjanggu.common.JDBCTemplate.close;
import static com.jjanggu.common.JDBCTemplate.getConnection;

public class Application3 {
    public static void main(String[] args) {

        // 최종 결과를 담을 변수
        EmployeeDTO selectedEmp = null;


        // 1. db 연결된 Connection 객체 필요
        Connection conn = getConnection();

        Statement stmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("조회하고자하는 사번을 입력해주세요 : ");
        String empId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id ='" +  empId + "'";

        //System.out.println(query);

        try {
            // 2. Statement 객체 생성
            stmt = conn.createStatement();
            // 3. sql문 실행 후 결과 받기
            rset = stmt.executeQuery(query);

            // 4. 결과 로직 처리
            if(rset.next()){
                //System.out.println(rset.getString("emp_id") + ", " + rset.getString("emp_name") + ", " + rset.getInt("salary"));

                selectedEmp = new EmployeeDTO(); // 조회결과가 있을 경우 생성이 진행

                // 조회된 한 행의 모든 컬럼값들을 해당 dto 객체의 각 필드에 담기
                selectedEmp.setEmpId(rset.getString("emp_id"));
                selectedEmp.setEmpName(rset.getString("emp_name"));
                selectedEmp.setEmpNo(rset.getString("emp_no"));
                selectedEmp.setEmail(rset.getString("email"));
                // ...
                selectedEmp.setSalary(rset.getInt("salary"));
                selectedEmp.setBonus(rset.getDouble("bonus"));
                selectedEmp.setHireDate(rset.getDate("hire_date"));
                // ...

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            // 5. 자원반납
            close(rset);
            close(stmt);
            close(conn);
        }


        if(selectedEmp == null){
            System.out.println("조회된 사원이 없습니다.");
        }else {
            System.out.println("조회된 사원이 있습니다.");
            System.out.println(selectedEmp);
        }


    }
}
