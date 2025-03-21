package com.ino.model.dao;

import com.ino.model.dto.CategoryDTO;
import com.ino.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static com.ino.common.JDBCTemplate.close;
import static com.ino.common.JDBCTemplate.getConnection;

public class MenuDAO {
    // 마지막 메뉴 번호 조회용

    private Properties prop = new Properties();
    public MenuDAO() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ino/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int selectLastMenuCode() {
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
            close(conn);
        }

        return lastMenuCode;
    }
    // 카테고리 전체 정보 조회용

    public List<CategoryDTO> getCategory(Connection conn) {
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
            close(conn);
        }
        for(CategoryDTO c: categoryDTOList) {
            System.out.println(c.toString());
        }
        return categoryDTOList;
    }

    /**
     *
     * @param conn - db 연결 connection 객체
     * @param menu - insert할 데이터들이 필드에 담긴 MenuDTO 객체
     * @return - insert 후 처리된 행 수
     */
    public int insertMenu(Connection conn, MenuDTO menu) {
        menu.setMenuCode(selectLastMenuCode() + 1);
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
            result = pstmt3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt3);
            close(conn);
        }
        return result;
    }
}
