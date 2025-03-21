package com.ino.run;

import com.ino.model.dto.CategoryDTO;
import com.ino.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.ino.common.JDBCTemplate.close;
import static com.ino.common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ino/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Connection conn = getConnection();

        PreparedStatement pstmt1 = null;
        ResultSet rset1 = null;
        int lastMenuCode = 0;

        String query1 = prop.getProperty("selectLastMenuCode");

        try {
            pstmt1 = conn.prepareStatement(query1);
            rset1 = pstmt1.executeQuery();
            if(rset1.next()) {
                lastMenuCode = rset1.getInt("last_menu_code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset1);
            close(pstmt1);
        }

        PreparedStatement pstmt2 = null;
        ResultSet rset2 = null;
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        String query2 = prop.getProperty("selectCategoryList");
        try {
            pstmt2 = conn.prepareStatement(query2);
            rset2 = pstmt2.executeQuery();
            while(rset2.next()) {
                CategoryDTO c = new CategoryDTO(rset2.getInt("category_code"), rset2.getString("category_name"));
                categoryDTOList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset2);
            close(pstmt2);
        }

        for(CategoryDTO c : categoryDTOList) {
            System.out.println(c.toString());
        }

        MenuDTO menu = new MenuDTO();
        menu.setMenuCode(lastMenuCode + 1);
        Scanner sc = new Scanner(System.in);
        System.out.print("insert menuName: ");
        menu.setMenuName(sc.nextLine());
        System.out.print("insert menuPrice: ");
        menu.setMenuPrice(sc.nextInt());
        System.out.print("insert category number: ");
        menu.setCategoryCode(sc.nextInt());
        sc.nextLine();
        System.out.println("insert orderable status: ");
        menu.setOrderableStatus(sc.nextLine());
        PreparedStatement pstmt3 = null;
        int result = 0;

        String query3 = prop.getProperty("insertMenu");

        try {
            pstmt3 = conn.prepareStatement(query3);
            pstmt3.setInt(1, menu.getMenuCode());
            pstmt3.setString(2, menu.getMenuName());
            pstmt3.setInt(3, menu.getMenuPrice());
            pstmt3.setInt(4, menu.getCategoryCode());
            pstmt3.setString(5, menu.getOrderableStatus());
            pstmt3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
