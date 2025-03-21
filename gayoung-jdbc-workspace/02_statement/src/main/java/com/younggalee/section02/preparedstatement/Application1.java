package com.younggalee.section02.preparedstatement;

import com.younggalee.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static com.younggalee.common.JDBCTemplate.close;
import static com.younggalee.common.JDBCTemplate.getConnection;

public class Application1 {
    /*
        ## java.sql.PreparedStatement ## >> 컨파일시
        이것만 쓰세요....******************************
        1. Statement의 하위 인터페이스 // 정적 >> run
        2. 미리 sql문을 담은 상태로 생성됨
        3. 플레이스 홀더(?)를 사용해 런타임 중에 매개변수에 값을 전달시킬 수 있음.
           >> 동적 쿼리(사용자의 입력값에 따라 매번 달라지는 쿼리) 생성에 유용
     */

    public static void main(String[] args) {
        // 사번으로 사원 조회 > 한 행 조회 > ResultSet > EmployeeDTO
        EmployeeDTO emp = null; // 최종 결과를 담아낼 변수
        Connection conn = getConnection(); // 1) db연결

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);

        System.out.println("조회하고자하는 사번을 입력해주세요 : ");
        String empId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id = ? ";

        try {
            // 2) sql문을 담은 채로 PreparedStatement 생성
            pstmt = conn.prepareStatement(query);

            // 현재 담겨있는 미완성된 sql문을 완성형으로 만들기 (사용자가 입력한 값을 ?에 채우기)
            // pstmt.setXXX(홀더순번, 대체할 값)
            pstmt.setString(1, empId);
            // 3) sql문 실행 후 결과 받기
            rset = pstmt.executeQuery();

            // 4) 결과 처리
            if(rset.next()){
                emp = new EmployeeDTO();
                emp.setEmpId(rset.getString("emp_id"));
                emp.setEmpName(rset.getString("emp_name"));
                emp.setEmail(rset.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
            close(conn);
        }

        if (emp == null){
            System.out.println("조회된 사원이 없습니다.");
        } else {
            System.out.println("조회된 사원이 있습니다.");
            System.out.println(emp);
        }
    }
}
