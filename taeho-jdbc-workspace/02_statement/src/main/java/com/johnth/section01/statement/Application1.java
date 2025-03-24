package com.johnth.section01.statement;

/*
    ## Statement ##
    1. 정적sql문을 실행하고 실행된 쿼리의 결과를 반환해주는 인스턴스
    2. 고정적인 쿼리 실행에 유용
    3. Connection 객체의 메서드를 통해 생성
    4. 쿼리 실행 메서드
        1) excuteQuery(sql)  : select 문 실행 => ResultSet 결과반환
        2) excuteUpdate(sql) : dml 문 실행 => int 결과반환(처리된 행수)

    ## ResultSet
    1. Select 문 실행 후 반환되는 데이터를 담고 있는 객체
    2. 주요 메서드
        1) next()       : 결과 행을 참조하기 위해 커서를 움직여주는 메서드
                          존재하는 행이 있으면 true, 없으면 false
        2) getInt()     : 현재 참조하는 행의 해당 컬럼 값을 Int형으로 변환
        3) getString()  : 현재 참조하는 행의 해당 컬럼 값을 String형으로 변환
        ... getDate() 등등
*/

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.johnth.common.JDBCTemplate.close;
import static com.johnth.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {

        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        String query = "select emp_id,emp_name from employee";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("emp_id") + " " + rs.getString("emp_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 유의사항: 생성한 자원 역순반환
            close(rs);
            close(stmt);
            close(conn);
        }
    }
}
