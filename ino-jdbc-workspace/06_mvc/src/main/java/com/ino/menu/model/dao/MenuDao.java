package com.ino.menu.model.dao;

import com.ino.menu.model.dto.CategoryDto;
import com.ino.menu.model.dto.MenuDto;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.ino.common.JDBCTemplate.close;

public class MenuDao {

    private Properties prop = new Properties();

    public MenuDao() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ino/mapper/menu-query.xml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MenuDto> selectAllMenu(Connection conn) {
        String query = prop.getProperty("selectAllMenu");
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<MenuDto> menuDtoList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                MenuDto menuDto = new MenuDto();
                int menuCode = rset.getInt("menu_code");
                String menuName = rset.getString("menu_name");
                int menuPrice = rset.getInt("menu_price");
                String categoryName = rset.getString("category_name");
                String orderableStatus = rset.getString("orderable_status");
                menuDto.setMenuName(menuName);
                menuDto.setMenuCode(menuCode);
                menuDto.setMenuPrice(menuPrice);
                menuDto.setCategory(categoryName);
                menuDto.setOrderableStatus(orderableStatus);
                menuDtoList.add(menuDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return menuDtoList;
    }

    public int insertMenu(MenuDto menu, Connection conn) {
        String query = prop.getProperty("insertMenu");
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, menu.getMenuName());
            pstmt.setInt(2, menu.getMenuPrice());
            pstmt.setString(3, menu.getCategory());
            pstmt.setString(4, menu.getOrderableStatus());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }

    public List<CategoryDto> getAllCategory(Connection conn) {
        List<CategoryDto> list = new ArrayList<>();
        String query = prop.getProperty("selectAllCategory");
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try {
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();
            while(rset.next()) {
                CategoryDto categoryDto = new CategoryDto(rset.getInt("category_code")
                        , rset.getString("category_name")
                        , rset.getInt("ref_category_code"));
                list.add(categoryDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int updateMenu(Connection conn, int menuCode, MenuDto requestParam) {
        String query = prop.getProperty("updateMenu");
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, requestParam.getMenuName());
            pstmt.setInt(2, requestParam.getMenuPrice());
            pstmt.setString(3, requestParam.getCategory());
            pstmt.setString(4, requestParam.getOrderableStatus());
            pstmt.setInt(5, menuCode);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }
}
