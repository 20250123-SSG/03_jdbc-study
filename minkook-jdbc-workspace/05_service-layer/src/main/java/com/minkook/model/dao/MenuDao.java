package com.minkook.model.dao;

import com.minkook.model.dto.CategoryDTO;
import com.minkook.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.minkook.common.JDBCTemplate.close;

public class MenuDao {

    private Properties prop = new Properties();

    public MenuDao(){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/minkook/mapper/menu-query.xml"));
        }catch (
                IOException e){
            e.printStackTrace();
        }
    }

    List<CategoryDTO> selectCategoryList(Connection conn){
        List<CategoryDTO> list = new ArrayList<>();
        PreparedStatement pstmt2 = null;
        ResultSet rset2 = null;

        String query2 = prop.getProperty("selectlastMenuList");

        try{
            pstmt2 = conn.prepareStatement(query2);
            rset2 = pstmt2.executeQuery();

            while (rset2.next()){
                CategoryDTO category = new CategoryDTO();
                category.setCategoryCode(rset2.getInt("category_code"));
                category.setCategoryName(rset2.getString("category_name"));
                list.add(category);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rset2);
            close(pstmt2);
        }
        return  list;
    }

    /**
     * 신규 메뉴를 등록해주는 메소드
     * @param conn -db와 연결된 Connectionb 객체
     * @param menu -insert할 데이터들이 각 필드에 담겨있는 MenuDTO 객체
     * @return  -insert 후 처리된 행수(삽입된 행 수)
     */
    public int insertMenu(Connection conn, MenuDTO menu) {
        PreparedStatement pstmt3 = null;
        int result = 0;
        String query3 = prop.getProperty("insertMenu");

        try {
            pstmt3 = conn.prepareStatement(query3);
            pstmt3.setInt(1, menu.getMenuCode());
            pstmt3.setString(2, menu.getMenuName());
            pstmt3.setInt(3, menu.getMenuPrice());
            pstmt3.setInt(4,menu.getCategoryCode());
            pstmt3.setString(5,menu.getOrderableStatus());
            result = pstmt3.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(pstmt3);
            close(conn);
        }

        return result;

    }
}
