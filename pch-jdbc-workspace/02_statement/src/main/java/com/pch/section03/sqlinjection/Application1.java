package com.pch.section03.sqlinjection;

import com.pch.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.pch.common.JDBCTemplate.close;
import static com.pch.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        // 사번만을 가지고만 사원 조회하기

        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rset = null;
        List<EmployeeDto> empDtoList = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Employee ID");
        String emp_id = sc.nextLine();

        String query = "select * from employee where emp_id = '" + emp_id+"'";

        try{
            stmt = conn.createStatement();
            rset = stmt.executeQuery(query);

            while (rset.next()) {
                empDtoList.add(new EmployeeDto());
                empDtoList.get(empDtoList.size()-1).setEmp_id(rset.getInt("emp_id"));
                empDtoList.get(empDtoList.size()-1).setEmp_name(rset.getString("emp_name"));
                empDtoList.get(empDtoList.size()-1).setEmp_no(rset.getString("emp_no"));
                empDtoList.get(empDtoList.size()-1).setEmail(rset.getString("email"));
                empDtoList.get(empDtoList.size()-1).setPhone(rset.getString("phone"));
                empDtoList.get(empDtoList.size()-1).setDept_code(rset.getString("dept_code"));
                empDtoList.get(empDtoList.size()-1).setJob_code(rset.getString("job_code"));
                empDtoList.get(empDtoList.size()-1).setSalary(rset.getDouble("salary"));
                empDtoList.get(empDtoList.size()-1).setBonus(rset.getDouble("bonus"));
                empDtoList.get(empDtoList.size()-1).setManager_id(rset.getString("manager_id"));
                empDtoList.get(empDtoList.size()-1).setHire_date(rset.getDate("hire_date"));
                empDtoList.get(empDtoList.size()-1).setQuit_date(rset.getDate("quit_date"));
                empDtoList.get(empDtoList.size()-1).setQuit_yn(rset.getString("quit_yn").charAt(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn);
        }

        for(EmployeeDto empDto : empDtoList){
            if(empDto != null) {
                System.out.println(empDto.toString());
            } else {
                System.out.println("조회된 사원이 없습니다.");
            }
        }

        /*
            ## SQL Injection ##
            1. 어플리케이션과 데이터베이스 간의 연동에서 발생될 수 있는 취약점
            2. 공격자가 악의적으로 조작된 쿼리를 삽입해 데이터베이스 정보를 불법적으로 조작할 수 있는 공격기법
            3. 사용자가 입력값에 SQL문을 직접 삽입해서 권한이 없는 명령을 실행할 수 있음
         */

    }
}
