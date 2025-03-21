package com.jjanggu.model.dao;

/*
    ## DAO ##
    1. Data Access Object
    2. 데이터베이스 접근용 객체
    3. CRUD 연산을 담당하는 메소드들의 집합으로 이루어진 클래스
    4. 실행할 sql문 별로 메소드로 따로 구성
 */

import com.jjanggu.model.dto.CategoryDTO;
import com.jjanggu.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.jjanggu.common.JDBCTemplate.close;

public class MenuDAO {

    private Properties prop = new Properties();

    public MenuDAO() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/jjanggu/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 마지막 메뉴 번호 조회용

    /**
     * 현존하는 메뉴들 중에 가장 마지막 메뉴 번호 조회해주는 메소드
     * @param conn - db와 연결된 Connection 객체
     * @return     - 조회된 마지막 메뉴 번호
     */
    public int selectLastMenuCode(Connection conn){
        PreparedStatement pstmt =  null;
        ResultSet rset = null;
        int lastMenuCode = 0; // 최종결과를 담을 변수

        String query1 = prop.getProperty("selectLastMenuCode");

        try {
            pstmt = conn.prepareStatement(query1);
            rset = pstmt.executeQuery();
            if(rset.next()){
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

    // 카테고리 전체 정보 조회용

    /**
     * 현존하는 카테고리의 전체 정보를 조회해주는 메소드
     * @param conn  - db와 연결된 Connection 객체
     * @return      - 조회결과가 담겨있는 LIST<CategoryDTO> 객체
     */
    public List<CategoryDTO> selectCategoryList(Connection conn){
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<CategoryDTO> list = new ArrayList<>(); // 최종 결과를 담을

        String query = prop.getProperty("selectCategoryList");

        try {
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();

            while (rset.next()){
                CategoryDTO category = new CategoryDTO();
                category.setCategoryCode(rset.getInt("category_code"));
                category.setCategoryName(rset.getString("category_name"));
                list.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }

        return list;
    }

    // 메뉴 등록용

    /**
     * 신규 메뉴를 등록해주는 메소드
     * @param conn  - db와 연결된 Connection 객체
     * @param menu  - insert 할 데이터들이 각 필드에 담여있는 MenuDTO 객체
     * @return      - insert 후에 처리된 행수 (삽입된 행 수)
     */
    public int insertMenu(Connection conn, MenuDTO menu){
        PreparedStatement pstm = null;
        int result = 0;

        String query = prop.getProperty("insertMenu");

        try {
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, menu.getMenuCode());
            pstm.setString(2, menu.getMenuName());
            pstm.setInt(3, menu.getMenuPrice());
            pstm.setInt(4, menu.getCategoryCode());
            pstm.setString(5, menu.getOrderableStatus());

            result = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstm);
        }
        return result;
    }



}
