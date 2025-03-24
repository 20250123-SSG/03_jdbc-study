package com.kyungbae.menu.model.dao;

import com.kyungbae.menu.model.dto.CategoryDto;
import com.kyungbae.menu.model.dto.MenuDto;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.kyungbae.common.JDBCTemplate.close;

public class MenuDao {

    private Properties prop = new Properties();

    public MenuDao(){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/kyungbae/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MenuDto> selectAllMenu(Connection conn) {
        // select문 여러행조회 => ResultSet => List
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<MenuDto> listAllMenu = new ArrayList<>();

        String query = prop.getProperty("selectAllMenu");

        try {
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                MenuDto menuDto = new MenuDto();
                menuDto.setMenuCode(rset.getInt("menu_code"));
                menuDto.setMenuName(rset.getString("menu_name"));
                menuDto.setMenuPrice(rset.getInt("menu_price"));
                menuDto.setCategory(rset.getString("category_name"));
                menuDto.setOrderableStatus(rset.getString("orderable_status"));
                listAllMenu.add(menuDto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return listAllMenu;
    }

    public int insertMenu(Connection conn, MenuDto menu) {
        int result = 0;
        PreparedStatement pstmt = null;

        String qurey = prop.getProperty("insertMenu");
        try {
            int num = 0;
            pstmt = conn.prepareStatement(qurey);
            pstmt.setString(++num, menu.getMenuName());
            pstmt.setInt(++num, menu.getMenuPrice());
            pstmt.setInt(++num, Integer.parseInt(menu.getCategory()));
            pstmt.setString(++num, menu.getOrderableStatus());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }

        return result;
    }

    public List<CategoryDto> selectAllCategory(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<CategoryDto> list = new ArrayList<>();

        String query = prop.getProperty("selectAllCategory");
        try {
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                list.add(new CategoryDto(
                        rset.getInt("category_code"),
                        rset.getString("category_name"),
                        rset.getInt("ref_category_code")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(pstmt);
        }
        return list;
    }

    public MenuDto selectMenuByCode(Connection conn, int menuCode) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        MenuDto menu = new MenuDto();

        String query = prop.getProperty("selectMenuByCode");
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, menuCode);
            rset = pstmt.executeQuery();

            if(rset.next()){
                menu.setMenuCode(rset.getInt("menu_code"));
                menu.setMenuName(rset.getString("menu_name"));
                menu.setMenuPrice(rset.getInt("menu_price"));
                menu.setCategory(rset.getString("category_code"));
                menu.setOrderableStatus(rset.getString("orderable_status"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return menu;
    }

    public int updateMenu(Connection conn, MenuDto modifyMenuInfo) {
        int result = 0;
        PreparedStatement pstmt = null;

        String query = prop.getProperty("updateMenu");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, modifyMenuInfo.getMenuName());
            pstmt.setInt(2, modifyMenuInfo.getMenuPrice());
            pstmt.setString(3, modifyMenuInfo.getCategory());
            pstmt.setString(4, modifyMenuInfo.getOrderableStatus());
            pstmt.setInt(5, modifyMenuInfo.getMenuCode());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }
}
