package com.sotogito.section03.sqlinjection;

import com.sotogito.common.JDBCTemplate;

import java.sql.*;
import java.util.Scanner;

import static com.sotogito.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {
        /// 사번만을 가지고 사원 조회하기 - Statement이용


        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("조회할 사번 : ");
        String inputEmpId = sc.nextLine();

        String query = "SELECT * FROM employee WHERE emp_id = ?";
        System.out.println(query);


        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, inputEmpId);
            rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(rs.getString("emp_name") + "," + rs.getString("job_code") + "," + rs.getString("salary"));
            }else {
                System.out.println("조회된 사원이 없습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCTemplate.close(rs);
            JDBCTemplate.close(ps);
            JDBCTemplate.close(conn);
        }

        /**
         * ## SQL Injection
         * 1. 애플리케이션과 데이터베이스 간의 연동에서 발생될 수 있는 취약점
         * 2. 공격자가 악의적으로 조작된 쿼리를 삽입해 데이터베이스 정보를 불법적으로 조적할 수 있는 공격 기법
         * 3. 사용자 입력값에 SQL문을 직접 삽입해 query를 완성시켜 조회되도록
         */

        /**
         * ## Statement VS PrepareStatement
         *
         * 1. 파라미터 바인딩
         *      - Statement : 파라미터 바인딩 불가, 쿼리 문자열 그대로 실행
         *      - PreparedStatement : ?(홀더)를 통해 파라미터 바인딩 가능 => 동적 쿼리 작업시 유용
         * 2. SQL Injection
         *      - Statement : SQL Injection에 취약
         *      - PrepareStatement : 파라미터 바인딩 방식이므로 SQL Injection 방지 가능
         *
         * ### 3. 컴파일 방식
         * - Statement : sql문이 실행될 때마다 db엔진에서 매번 새롭게 컴파일
         * - PrepareStatement : 최초에 한번만 컴파일되고 캐싱되어 재사용됨 => 동일한 쿼리문을 반복실행할 때 더 빠름
         */
    }
}
