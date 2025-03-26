package com.sotogito.model.dao;

import com.sotogito.model.dto.CategoryDTO;
import com.sotogito.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static com.sotogito.common.JDBCTemplate.close;


public class MenuDAO {
    private final static String xmlPath = "src/main/java/com/sotogito/mapper/menu-query.xml";
    private final Properties prop = new Properties();

    public MenuDAO() {
        try {
            prop.loadFromXML(new FileInputStream(xmlPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertMenu(Connection conn, MenuDTO menuDTO) {
        PreparedStatement ps = null;
        int insertResult = 0;

        String query = prop.getProperty("insertMenu");
        try {
            ps = conn.prepareStatement(query);

            ps.setString(1, menuDTO.getMenuName());
            ps.setInt(2, menuDTO.getMenuPrice());
            ps.setInt(3, menuDTO.getCategoryCode());
            ps.setString(4, menuDTO.getOrderableStatus());

            insertResult = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(ps);
//            close(conn);
        }
        return insertResult;
    }


    ///  신규 카테고리 등록용
    public int insertCategory(Connection conn, CategoryDTO categoryDTO) {
        PreparedStatement ps = null;
        int insertResult = 0;

        String query = prop.getProperty("insertCategory");
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, categoryDTO.getCategoryName());
            ps.setObject(2, categoryDTO.getRefCategoryCode()); //Integer를 setInt 로 넣어줄 경우 SQL에서 숫자로 변경할 때 예외 발생 가능성 있음

            insertResult = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(ps);
        }
        return insertResult;
    }

    public int selectCurrentCategoryCode(Connection conn) {
        ///  select 한 행
        /// Statement -> Resultset -> int

        int result = 0;

        PreparedStatement ps = null;
        ResultSet rs = null; //값을 받아와야함

        String query = prop.getProperty("selectCurrentCategoryCode");
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getInt("curr_category_code"); //값을 넣는게 아니라 받아오는거임
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(ps);
            close(rs);
        }

        return result;
    }

}
