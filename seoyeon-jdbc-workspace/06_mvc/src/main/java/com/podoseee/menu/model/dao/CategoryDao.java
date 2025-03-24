package com.podoseee.menu.model.dao;

import com.podoseee.menu.model.dto.CategoryDto;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.podoseee.common.JDBCTemplate.close;


public class CategoryDao {

    private Properties prop = new Properties();

    public CategoryDao(){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/podoseee/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<CategoryDto> selectAllCategory(Connection conn){
        List<CategoryDto> list = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("selectAllCategory");


        try{
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();

            while(rset.next()){
                list.add(new CategoryDto(
                        rset.getInt("category_code"),
                        rset.getString("category_name"),
                        rset.getInt("ref_category_code")
                ));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally{
            close(rset);
            close(pstmt);
        }
        return list;
    }

    public int insertCategory(Connection conn, CategoryDto category) {

        int result = 0;
        PreparedStatement pstmt = null;
        String query = prop.getProperty("insertCategory");

        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, category.getCategoryCode());
            pstmt.setString(2, category.getCategoryName());
            pstmt.setInt(3, category.getRefCategoryCode());

            result = pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            close(pstmt);
        }

        return result;
    }
}


