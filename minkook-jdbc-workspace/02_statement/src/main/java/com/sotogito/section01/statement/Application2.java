package com.sotogito.section01.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.sotogito.common.JDBCTemplate.close;
import static com.sotogito.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {

        /// 1. conn 객체 생성
        Connection conn = getConnection();

        Statement stmt = null;
        ResultSet rs = null;

        String query = "SELECT emp_id,emp_name,salary FROM employee WHERE emp_id = '201'";

        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){ /// 쿼리로 반환되는 값이 하나라 WHILE이 아님
                int emp_id = rs.getInt("emp_id");
                String emp_name = rs.getString("emp_name");
                double salary = rs.getDouble("salary");

                System.out.println(emp_id + " " + emp_name + " " + salary);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            close(rs);
            close(stmt);
            close(conn);
        }
    }
}
