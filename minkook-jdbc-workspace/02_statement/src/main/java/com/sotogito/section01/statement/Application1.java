package com.sotogito.section01.statement;

import com.sotogito.common.JDBCTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Application1 {
    public static void main(String[] args) {

        /**
         * ## java.sql.Statement
         * 1. 정적 sql문을 실행하고 실행된 쿼리의 결과를 반환해주는 인스턴스
         * 2. 고정적인 쿼리 실행에 유용
         * 3. Connection 객체에 메서드를 통해 생성
         * 4. 쿼리 실행 메서드
         *      1) executeQuery(sql문)   :   select문 실행 => ResultSet 결과 반환
         *      2) executeUpdate(sql문)  :  dml문 실행 => int 결과 반환
         *
         *
         *
         * ## java.sql.ResultSet
         * 1. Select문 실행 후 반환되는 데이터를 담고 있는 객체
         * 2. 주요 메서드
         *      1) next() : 결과 행을 참조하기 위해 커서를 움직여주는 메서드 (다음row)
         *                  존재하는 행 있으면 true / 없 false
         *      2) getInt(컬럼) : 현재 참조하는 행의 해당 컬럼의 값을 int형으로 반환
         *      3) getString(컬럼) :                  "           값을 String으로 반환
         *      4) getDate(컬럼) :                    "           값을 java.sql.Date타입으로 반환
         */



        Connection conn = JDBCTemplate.getConnection();
        Statement stmt = null; /// 쿼리문 실행 결과 받는 객체
        ResultSet rset = null; /// select문 실행 결과 담는 객체

        String query = "SELECT emp_id,emp_name FROM employee"; ///세미클론 금지

        try {
            stmt = conn.createStatement(); ///statement객체 생성
            rset = stmt.executeQuery(query); ///sql문 실행 및 결과받기

            /// 결과 처리 로직
            while (rset.next()) {
                System.out.println(rset.getString("emp_id") + " : "+rset.getString("emp_name"));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            /**
             * ### 유의할 점
             *     자원반납은 생성된 역순으로 반납해줘야함
             */
            JDBCTemplate.close(rset);
            JDBCTemplate.close(stmt);
            JDBCTemplate.close(conn);
        }

    }
}
