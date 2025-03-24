package com.seungjoo.section01.insert;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.seungjoo.common.JDBCTemplate.close;
import static com.seungjoo.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {


        Connection conn = getConnection();
        PreparedStatement psmt = null;
        int result = 0;

        Properties prop = new Properties();
        try {
            prop.loadFromXML((new FileInputStream(("src/main/java/com/seungjoo/mapper/menu-query.xml"))));
        } catch (IOException e) {
            e.printStackTrace();

        }
        String query = prop.getProperty("insertMenu");

        try {
            psmt = conn.prepareStatement(query);
            psmt.setString(1, "봉골레청국장");
            psmt.setInt(2,50000);
            psmt.setInt(3,4);
            psmt.setString(4,"Y");

            result = psmt.executeUpdate();




        } catch (SQLException e) {
            e.printStackTrace();

        }finally {
            close(psmt);
            close(conn);
        }

    }
}
