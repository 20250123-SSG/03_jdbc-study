package com.younggalee.section01.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.younggalee.common.JDBCTemplate.close;
import static com.younggalee.common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {
        /*
        java.sql.Statement

        1. 정적 sql문을 실행하고 실행된 쿼리의 결과를 반환해주는 인스턴스
        2. 고정적인 쿼리 실행에 유횽
        3. Connection 객체의 메소드를 통해 생성
        4. 쿼리 실행 메소드
            1) executeQuery(sql문) : select문 실행 => ResultSet 결과 반환
            2) executeUpdate(sql문) : dml문 실행

        java.sql.ResultSet
        1. SELECT문 실행 후 반환되는 데이터를 담고 있는 객체
        2. 주요 메소드
            1) next(): 결과 행을 참조하기 위해 커서를 움직여주는 메소드
                    존재하는 행이 있으면 true, 없으면 false 반환
            2) getInt(컬럼) : 현재 참조하는 행의 해당 컬럼의 값을 Int형으로 반환
            3) getString(컬럼) : 현재 참조하는 행의 해당 컬럼의 값을 String형으로 반환
            4) getrDate(컬럼) : 현재 참조하는 행의 해당 컬럼의 값을 java.sql.Date형으로 반환
         */


        Connection conn = getConnection();

        Statement stmt = null; // 쿼리문 실행 후 결과 받는 객체
        ResultSet rset = null; // select문 실행 결과를 담는 객체

        String query = "SELECT emp_id, emp_name FROM employee"; // string에 세미콜론 안됨


        try {
            stmt = conn.createStatement(); // Statment 객체 생성
            rset = stmt.executeQuery(query); // sql문 실행 및 결과 받기

            // 결과 처리 로직

            while (rset.next()) {
                System.out.println(rset.getString("emp_id")+ ", " + rset.getString("emp_name"));
            }


        } catch (SQLException e) {
            e.printStackTrace();

        }finally {
            // 유의할점 : 생성된 역순으로 반납
            close(rset);
            close(stmt);
        }


    }
}