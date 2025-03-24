package com.seungjoo.common;

import com.seungjoo.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.seungjoo.common.JDBCTemplate.close;
import static com.seungjoo.common.JDBCTemplate.getConnection;


public class Application3 {
    public static void main(String[] args) {

        EmployeeDTO selectdEmp = null;
    /*
    //java.sql.Statement
    1. 정적 sql문을 실행하고 실행된 쿼리의 결과를 반환해주는 인스턴스
    2. 고정적인 쿼리 실행에 유용
    3. Connection 객체의 메소드를 통해 생성
    4. 쿼리 실행 메소드
        1) executeQuery(sql문) : select문 실행 -> ResultSet 결과 반환
        2) executeUpdate(sql문) : dml문 실행
     */
        Connection conn = getConnection();
        Statement stmt = null; //쿼리문 실행 후 결과 받는 객체
        ResultSet rset = null; //select문 실행 결과를 담는 객체

        String query = "SELECT * FROM employee WHERE emp_id = 1";


        try {
            stmt = conn.createStatement(); //객체 생성
            rset = stmt.executeQuery(query); //sql문 실행 및 결과받기

            //결과처리로직
            /*
            ResultSet에 의 메서드 next() : 결과 행을 참조하기 위해 커서를 움직여주는 메소드, 존재하는 행이 잇으면 true, 없으면 false반환
               getInt(컬럼) : 현재 참조하는 행의 해당 컬럼의 값을 Int형으로 반환
             */
            if (rset.next()) {

                selectdEmp = new EmployeeDTO(); //조회결과가 있을 경우 생성이 진행

                //조회된 한 행의 모든 컬럼값들은 해당 dto의 각 필드에 담기
                selectdEmp.setEmpId(rset.getString("emp_id"));
                selectdEmp.setEmpName(rset.getString("emp_name"));
                selectdEmp.setEmpNo(rset.getString("emp_no"));
                selectdEmp.setEmail(rset.getString("email"));
                selectdEmp.setPhone(rset.getString("phone"));
                selectdEmp.setSalary(rset.getInt("salary"));
                selectdEmp.setHireDate(rset.getDate("hire_date"));
                selectdEmp.setBonus(rset.getDouble("bonus"));






            }

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //생성된 역순으로 자원반납
            close(rset);
            close(stmt);
            close(conn);

        }
        if(selectdEmp == null) {
            System.out.println("조회된 사원이 없습니다");
        }else{
            System.out.println("조회된 사원이 없습니다.");
            System.out.println(selectdEmp);
        }

    }
}