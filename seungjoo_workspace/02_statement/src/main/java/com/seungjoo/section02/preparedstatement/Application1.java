package com.seungjoo.section02.preparedstatement;

import com.seungjoo.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static com.seungjoo.common.JDBCTemplate.close;
import static com.seungjoo.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        /*
        ## java.sql.PreparedStatement
        1. 걍 이거 쓰세요. 성능적으로 이게 좋음
        2. Statement의 하위 인터페이스
        3. 미리 sql문을 담은 상태로 생성됨
        3. 플레이스홀더(?)를 사용해 런타임중에 매개변수에 값을 전달시킬 수 있음
        -> 동적 쿼리(사용자의 입력값에 따라 매번 달라지는 쿼리) 생성에 유용

         */

    //사번으로 사원 조회 -> 한 행 조회 -> ,ResultSet -> EmployeeDto

        EmployeeDTO emp = null;
        Connection conn = getConnection(); //db연결

        PreparedStatement psmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("조회하고자 하는 사번 입력:");
        String empId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id = ?"; // ?가 홀더임, 매개변수같은 개념임

        try {
            psmt = conn.prepareStatement(query); //2)sql문을 담은 채로 PreparedStatement 생성

        // 현재 담겨있는 미완성된 sql문을 완성형으로 만들기(사용자가 입력한 값을 ?에 채우기)
        psmt.setString(1, empId); //(홀더 순번, 대체할값)

        psmt.executeQuery(); //sql문 실행 후 결과 받기

        if(rset.next()) {
            emp = new EmployeeDTO();
            emp.setEmpId(empId);
            emp.setEmpName(rset.getString("emp_name"));
            emp.setEmail(rset.getString("email"));
        }



        } catch (SQLException e) {
            e.printStackTrace();

        }finally {
            close(rset);
            close(psmt);
            close(conn);
        }
        if(emp == null){
            System.out.println("조회된 사원이 없습니다.");
        }else{
            System.out.println(emp);
        }


    }
}
