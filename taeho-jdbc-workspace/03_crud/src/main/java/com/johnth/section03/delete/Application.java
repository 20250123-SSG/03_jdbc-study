package com.johnth.section03.delete;

import com.johnth.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.johnth.common.JDBCTemplate.close;
import static com.johnth.common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("삭제할 메뉴 번호: ");
        int menuCode = sc.nextInt();
        sc.nextLine();

        Connection conn = getConnection();
        PreparedStatement ps = null;
        int result = 0;

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/johnth/mapper/menu_query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String query = prop.getProperty("deleteMenu");
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, menuCode);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }

        if (result > 0){
            System.out.println("삭제완");
        } else {
            System.out.println("삭제실패");
        }
    }
}
