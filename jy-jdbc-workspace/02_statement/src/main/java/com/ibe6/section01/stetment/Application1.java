package com.ibe6.section01.stetment;

import com.ibe6.common.JDBCTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.ibe6.common.JDBCTemplate.close;
import static com.ibe6.common.JDBCTemplate.getConnection;

public class Application1 {

    public static void main(String[] args) {

        /*
            ## java.sql.Statement ##
            1. 정적 sql문을 실행하고 실행된 쿼리의 결과를 반환해주는 인스턴스
            2. 고정적인 쿼리 실행에 유용
            3. Connection 객체의 메소드를 통해 생성
            4. 쿼리 실행 메소드
             1) executeQuery(sql문) : select문 실행 => ResultSet
             2) executeUpdate(sql문) : dml 문 실행 => int 반환

            ## java.sql.ResultSet ##
            1. SELECT문 실행 후 반환되는 데이터를 담고 있는 객체
            2. 주요 메서드
                1) next() : 결과행을 참조하기 위해 커서를 움직여 주는 메소드
                            존재하는 행이 있으면 true, 없으면 false 반환
                2) getInt(컬럼) : 현재 참조하는 행의 해당 컬럼의 값을 Int 형으로 반환
                3) getString(컬럼) : 현재 참조하는 행의 해당 컬럼의 값을 String형으로 반환
                4) getDate(컬럼) : 현재 참조하는 행의 해당 컬럼의 값을 java.sql.Date 형으로 반환
         */

        Connection conn = getConnection();
        Statement stmt = null; // 쿼리문 실행 결과를 받는 객체
        ResultSet rset = null; // select문 실행 결과를 담는 객체

        // 결과 처리 로직
        String query =  "SELECT emp_id, emp_name FROM employee";

        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(query);

            // 결과 처리 로직
            while(rset.next()) {
                int emp_id = rset.getInt("emp_id");
                String emp_name = rset.getString("emp_name");
                System.out.println(emp_id + " " + emp_name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn);
        }
    }
}
