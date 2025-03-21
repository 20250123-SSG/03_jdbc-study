package com.ibe6.section03.sqlinjection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.ibe6.common.JDBCTemplate.close;
import static com.ibe6.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
            // 사번만을 가지고만 사원 조회하기 (Statement 이용)

        Connection conn = getConnection();

        Statement stmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("조회할 사번: ");
        String empId = sc.nextLine(); // ' OR emp_name = 'xxx   입력

        String query = "SELECT * FROM employee WHERE emp_id = '" + empId + "'";
        System.out.println(query);

        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(query);

            if(rset.next()){
                System.out.println(rset.getString("emp_name") + ", " + rset.getString("dept_code") + ", " + rset.getInt("salary"));
            }else{
                System.out.println("조회된 사원이 없습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(stmt);
            close(conn);
        }

        /*

            ## SQL Injection ##
            1. 애플리케이션과 데이터베이스 간의 연동에서 발생될 수 있는 취약점
            2. 공격자가 악의적으로 조작된 쿼리를 삽입해 데이터베이스 정보를 불법적으로 조작할 수 있는 공격 기법
            3. 사용자 입력값에 SQL문을 직접 삽입해서 권한이 없는 명령을 실행할 수 있음


         */
    }
}
