package com.sotogito.section02.template;

import java.sql.Connection;
import java.sql.SQLException;

import static com.sotogito.section02.template.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {
       // Connection conn = JDBCTemplate.getConnection();
        Connection conn = getConnection();
        System.out.println(conn);

        ///  --sql문 실행 완료

        JDBCTemplate.close(conn);
    }
}
