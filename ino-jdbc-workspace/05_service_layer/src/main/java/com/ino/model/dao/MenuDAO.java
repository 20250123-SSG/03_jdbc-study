package com.ino.model.dao;

import com.ino.model.dto.CategoryDTO;
import com.ino.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static com.ino.common.JDBCTemplate.close;

public class MenuDAO {

    private Properties prop = new Properties();
    public MenuDAO() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ino/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int insertMenu(Connection conn, MenuDTO menu) {
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            pstmt = conn.prepareStatement(prop.getProperty("insertMenu"));
            pstmt.setString(1, menu.getMenuName());
            pstmt.setInt(2, menu.getMenuPrice());
            pstmt.setInt(3, menu.getCategoryCode());
            pstmt.setString(4, menu.getOrderableStatus());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int selectCurrentCategoryCode(Connection conn) {
        int currentCategoryCode = 0;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("selectCurrentCategoryCode");

        try {
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();
            if(rset.next()) {
                currentCategoryCode = rset.getInt("LAST_INSERT_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return currentCategoryCode;
    }

    public int insertCategory(Connection conn, CategoryDTO category) {
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            pstmt = conn.prepareStatement(prop.getProperty("insertMenu"));
            pstmt.setString(1, category.getCategoryName());
            pstmt.setInt(2, category.getCategoryCode());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }
}
