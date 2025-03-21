package com.jjanggu.model.dao;

import com.jjanggu.model.dto.CategoryDTO;
import com.jjanggu.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.jjanggu.common.JDBCTemplate.close;

public class MenuDAO {

    private Properties prop = new Properties();

    public MenuDAO(){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/jjanggu/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 신규 메뉴 추가용
    public int insertMenu(Connection conn, MenuDTO menu){
        PreparedStatement pstmt = null;
        int result = 0; // 처리 결과를 받을 변수

        String query = prop.getProperty("insertMenu");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, menu.getMenuName());
            pstmt.setInt(2, menu.getMenuPrice());
            pstmt.setInt(3, menu.getCategoryCode());
            pstmt.setString(4, menu.getOrderableStatus());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
        }

        return result;

    }

    // 신규 카테고리 등록용
    public int insertCategory(Connection conn, CategoryDTO category){
        PreparedStatement pstmt = null;
        int result = 0;

        String query = prop.getProperty("insertCategory");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, category.getCategoryName());
            pstmt.setObject(2, category.getRefCategoryCode());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
        }
        return result;

    }
}
