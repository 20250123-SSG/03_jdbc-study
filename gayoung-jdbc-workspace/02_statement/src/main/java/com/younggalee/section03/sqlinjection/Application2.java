package com.younggalee.section03.sqlinjection;

import java.sql.*;
import java.util.Scanner;

import static com.younggalee.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("조회할 사번 : ");
        String empId = sc.nextLine();
        String query = "SELECT * FROM employee WHERE emp_id = ?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, empId); // 바인딩 시킨다.
            rset = pstmt.executeQuery();

            if(rset.next()){
                System.out.println(rset.getString("emp_name") + ", " + rset.getString("dept_code") +", " + rset.getInt("salary"));
            } else{
                System.out.println("조회된 사원이 없습니다. ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
            ## Statement VS PreparedStatement
            1. 파라미터 바인딩
            - Statement     : 파라미터 바인딩 불가, 쿼리 문자열 그대로 실행 (정적 쿼리 like DDL에만 사용하는 것이 좋음)
            - PreparedStatement : ?(홀더)를 통해 파라미터 바인딩 가능 >> 동적 쿼리 작업시 유용
            2. SQL Injection
            - Statement     : SQL Infection에 취약
            - PreparedStatement : 파라미터 바인딩 방식이므로 sql injection 방지 기능
            3. 컴파일 방식
            - Statement     : sql문 실행될때마다 데이터베이스 엔진에서 매번 새롭게 컴파일
            - PreparedStatement : 최초 1번만 컴파일 되고 캐싱(기록)되어 재사용됨 >> 동일한 쿼리를 반복실행할 때 더 빠름 (성능이 좋음)
         */

    }
}
