package com.ino.section01.insert;

import com.ino.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ino.common.JDBCTemplate.close;
import static com.ino.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MenuDTO newMenu = new MenuDTO();
        System.out.print("set name : ");
        newMenu.setMenuName(sc.nextLine());
        System.out.print("set menu price: ");
        newMenu.setMenuPrice(sc.nextInt());
        System.out.println("set category code: ");
        newMenu.setCategoryCode(sc.nextInt());
        System.out.println("set orderablestatus: ");
        sc.nextLine();
        newMenu.setOrderableStatus(sc.nextLine());
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ino/mapper/menu-query.xml"));
            pstmt = conn.prepareStatement(prop.getProperty("insertMenu"));
            pstmt.setString(1, newMenu.getMenuName());
            pstmt.setInt(2, newMenu.getMenuPrice());
            pstmt.setInt(3, newMenu.getCategoryCode());
            pstmt.setString(4, newMenu.getOrderableStatus());

            result = pstmt.executeUpdate();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }
    }
}
