package com.johnth.section01.insert;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.johnth.common.JDBCTemplate.close;
import static com.johnth.common.JDBCTemplate.getConnection;


public class Application1 {
    public static void main(String[] args) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        int result = 0;

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/johnth/mapper/menu_query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String query = prop.getProperty("insertMenu");

        try{

            ps = con.prepareStatement(query);
            ps.setString(1, "봉골레파스타");
            ps.setInt(2, 30000);
            ps.setInt(3, 4);
            ps.setString(4, "Y");

            result = ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            close(ps);
            close(con);
        }
    }
}
