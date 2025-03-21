package com.minkook.secion01.insert;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.minkook.common.JDBCTemplate.close;
import static com.minkook.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {

        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        int result = 0; //처리된 행 수를 받기 위한 변수

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/sotogito/mapper/menu-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String query = prop.getProperty("insertMenu");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "봉골레청국장"); // 물음표 첫번재
            pstmt.setInt(2,50000); //두번째
            pstmt.setInt(3, 4);
            pstmt.setString(4,"Y");

            result  = pstmt.executeUpdate();//처리된 행수 반환

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(pstmt);
            close(conn);
        }
        System.out.println(result);
    }
}
