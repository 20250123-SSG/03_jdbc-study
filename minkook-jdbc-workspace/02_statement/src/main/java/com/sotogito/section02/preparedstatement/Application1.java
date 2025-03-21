package com.sotogito.section02.preparedstatement;

import com.sotogito.model.dto.EmployeeDTO;

import java.sql.*;
import java.util.Collections;
import java.util.Scanner;

import static com.sotogito.common.JDBCTemplate.close;
import static com.sotogito.common.JDBCTemplate.getConnection;

public class Application1 {

    /**
     * 이
     * 것
     * 만
     * 써
     * ## java.sql.PrepareStatement
     * 1. Statement 의 하위 인터페이스
     * 2. 미리 sql문을 담은 상태로 생성됨
     * 3. 플레이스홀더(?)를 사용해 런타임 중에 매개변수에 값을 전달시킬 수 있음
     *      => 동적 쿼리(사용자의 입력 값에 따라 매번 달라지는 쿼리) 생성에 유용
     */


    public static void main(String[] args) {

        EmployeeDTO emp = null; /// 최종 결과를 담아낼 변수

        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter employee ID");
        String inputEmpId = sc.nextLine();


        String query = "SELECT * FROM employee WHERE emp_id = ?";

        try {
            pstmt = conn.prepareStatement(query); /// 쿼리를 같이 넘기면서 스태이트먼트 객체를 생성한다.

            ///  현재 담겨있는 미완성된 sql문을 완성형으로 만들기(사용자가 입력한 값을 ?에 채우기)
            pstmt.setString(1, inputEmpId); //사용자가 입력한 id값을 쿼리의 첫번째 ?에 채워넣는다.

            rset = pstmt.executeQuery(); /// sql문 결과 받기

            /// 결과 처리
            if(rset.next()) {
                emp = new EmployeeDTO(); //생성자로 초기화하는게 좋지만 값이 너무 많아서 setter 사용
                emp.setEmpId(rset.getString("emp_id"));
                emp.setEmpName(rset.getString("emp_name"));
                emp.setEmail(rset.getString("email"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(rset);
            close(pstmt);/// Statement >PrepareStatement 때문에 자식이 그대로 인수로 넘겨짐 ㄱㄴ
            close(conn);
        }

        if (emp == null) {
            System.out.println("업다");
            return;
        }
        System.out.println("있다.");
        System.out.println(emp);
    }
}
