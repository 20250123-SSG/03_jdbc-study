package com.ino.section01.statement;

import com.ino.common.JDBCTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.ino.common.JDBCTemplate.close;
import static com.ino.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {
        Connection conn = getConnection();
        ResultSet rset = null;
        Statement stmt = null;
        String sql = "SELECT emp_id FROM employee WHERE emp_id = 201";
        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            if(rset.next()) {
                System.out.println("emp_id: " + rset.getInt("emp_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(stmt);
            close(conn);
        }
    }
}
