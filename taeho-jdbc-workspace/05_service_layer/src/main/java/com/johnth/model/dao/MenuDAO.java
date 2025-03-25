package com.johnth.model.dao;

import com.johnth.model.dto.CategoryDTO;
import com.johnth.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.johnth.common.JDBCTemplate.close;
import static com.johnth.common.JDBCTemplate.getConnection;

/*
    ## DAO(Data Access Object)
    - 데이터베이스 접근용 객체
    - CRUD 연산을 담당하는 메서드들의 집합으로 이뤄진 클래스
    - 실행할 sql문 별로 메서드 구성
 */
public class MenuDAO {
    private Properties prop = new Properties();
    Connection conn = getConnection();

    public MenuDAO() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/johnth/mapper/menu_query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param conn
     * @param menu
     * @return
     */
    public int insertMenu(Connection conn, MenuDTO menu) {
        PreparedStatement ps = null;
        int result = 0;

        String query = prop.getProperty("insertMenu");

        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, menu.getMenu_name());
            ps.setInt(2, menu.getMenu_price());
            ps.setInt(3, menu.getCategory_code());
            ps.setString(4, menu.getOrderable_status());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        return result;
    }

    /**
     * @param conn
     * @return
     */
    public int selectLastMenuCode(Connection conn) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int lastMenuCode = 0;

        String query = prop.getProperty("selectLastMenuCode");

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                lastMenuCode = rs.getInt("last_menu_code");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
        }
        return lastMenuCode;
    }

    /**
     * @param conn
     * @return
     */
    public List<CategoryDTO> selectCategoryList(Connection conn) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CategoryDTO> list = new ArrayList<CategoryDTO>();

        String query = prop.getProperty("selectCategoryList");

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                CategoryDTO category = new CategoryDTO();
                category.setCategoryCode(rs.getInt("category_code"));
                category.setCategoryName(rs.getString("category_name"));
                list.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
        }

        for (CategoryDTO category : list) {
            System.out.println(category);
        }
        return list;
    }

    public int insertCategory(Connection conn, CategoryDTO category) {
        PreparedStatement ps = null;
        int result = 0;
        String query = prop.getProperty("insertCategory");
        try {
            ps = conn.prepareStatement(query);
            ps.setObject(1, category.getCategoryName());
            ps.setObject(2, category.getCategoryCode());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
        }
        return result;
    }


    public int selectCurrentCategoryCode(Connection conn) {
        int currentCategoryCode = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = prop.getProperty("selectCurrentCategoryCode");

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                currentCategoryCode = rs.getInt("current_category_code");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
        }
        return currentCategoryCode;

    }
}
