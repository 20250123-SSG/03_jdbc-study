package com.johnth.section02.update;

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
        System.out.print("변경 메뉴 번호: ");
        int menuCode = sc.nextInt();
        sc.nextLine();
        System.out.print("변경메뉴명: ");
        String menuName = sc.nextLine();
        System.out.print("변경 메뉴 가격: ");
        int menuPrice = sc.nextInt();
        sc.nextLine();

        MenuDTO menu = new MenuDTO(menuCode, menuName, menuPrice);

        Connection conn = getConnection();
        PreparedStatement ps = null;
        int result = 0;

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/johnth/mapper/menu_query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String query = prop.getProperty("updateMenu");
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, menu.getMenu_name());
            ps.setInt(2, menu.getMenu_price());
            ps.setInt(3, menu.getMenu_code());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        
        if (result > 0){
            System.out.println("수정완");
        } else {
            System.out.println("수정 실패");
        }
    }
}
