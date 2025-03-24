package com.kyungbae.model.dao;

import com.kyungbae.model.dto.CategoryDTO;
import com.kyungbae.model.dto.MenuDTO;

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

public class MenuDAO {

    // Properties 파일을 전역에서 사용하기 때문에 field에 생성
    private Properties prop = new Properties();

    // 기본생성자를 생성할때 쿼리 문 불러오기
    public MenuDAO(){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/kyungbae/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 마지막 메뉴 번호 조회용 메소드

    /**
     * 마지막 메뉴 번호 조회하는 메소드
     * @param conn - DB와 연결된 Connection 객체
     * @return     - 조회된 마지막 메뉴 번호
     */
    public int selectLastMenuCode(Connection conn){
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        int lastMenuCode = 0;

        String query1 = prop.getProperty("selectLastMenuCode");
        try {
            pstmt = conn.prepareStatement(query1);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                lastMenuCode = rset.getInt("last_menu_code");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return lastMenuCode;
    }


    // 카테고리 전체 정보 조회용 메소드

    /**
     * 카테고리의 전체 정보를 조회하는 메소드
     * @param conn - DB와 연결된 Connection 객체
     * @return     - 카테고리 전체 정보가 담긴 CategoryDTO list
     */
    public List<CategoryDTO> selectCategoryList(Connection conn){
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<CategoryDTO> listCategory = new ArrayList<>();

        String query2 = prop.getProperty("selectCategoryList");
        try {
            pstmt = conn.prepareStatement(query2);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                CategoryDTO category = new CategoryDTO(rset.getInt("category_code"), rset.getString("category_name"));
                listCategory.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }

        return listCategory;
    }


    // 메뉴 등록용 메소드

    /**
     * 입력한 메뉴를 등록하는 메소드
     * @param conn - DB와 연결된 Connection 객체
     * @param menu - 입력할 메뉴가 담겨있는 MenuDTO
     * @return     - 삽입된 row수 반환
     */
    public int insertMenu(Connection conn, MenuDTO menu){
        PreparedStatement pstmt = null;
        int result = 0;

        String query = prop.getProperty("insertMenu");

        try {
            pstmt = conn.prepareStatement(query);
            int num = 0;
            pstmt.setInt(++num, menu.getMenuCode());
            pstmt.setString(++num, menu.getMenuName());
            pstmt.setInt(++num, menu.getMenuPrice());
            pstmt.setInt(++num, menu.getCategoryNo());
            pstmt.setString(++num, menu.getOrderable());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }

        return result;
    }
}
