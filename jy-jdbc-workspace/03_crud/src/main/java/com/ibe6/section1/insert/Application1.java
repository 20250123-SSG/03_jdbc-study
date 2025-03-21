package com.ibe6.section1.insert;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.ibe6.common.JDBCTemplate.close;
import static com.ibe6.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) throws SQLException {

        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        int result = 0; // 처리된 행수 (실행결과)를 받기 위한 변수

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ibe6/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String query = prop.getProperty("insertMenu");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "봉골레청국장");
            pstmt.setInt(2, 50000);
            pstmt.setInt(3, 4);
            pstmt.setString(4, "Y");

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }

        System.out.println("result: " + result);
    }
}
