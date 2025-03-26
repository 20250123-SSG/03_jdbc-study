package com.kyungbae.model.dao;

import com.kyungbae.model.dto.CategoryDTO;
import com.kyungbae.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static com.kyungbae.common.JDBCTemplate.close;

public class MenuDAO {

    // Properties 파일을 전역에서 사용하기 때문에 field에 생성
    private Properties prop = new Properties();

    // 기본생성자를 생성할때 xml에 저장된 쿼리 문 불러오기
    public MenuDAO(){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/kyungbae/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    // 신규 카테고리 등록용 메소드

    /**
     * 신규 카테고리를 등록하는 메소드
     * @param conn      - 등록하는 Connection 객체
     * @param category  - 입력할 CategoryDTO 정보
     * @return          - 등록 완료된 row 수
     */
    public int insertCategory(Connection conn, CategoryDTO category){
        PreparedStatement pstmt = null;
        int result = 0;

        String query = prop.getProperty("insertCategory");

        try {
            int num = 0;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(++num, category.getCategoryName());
            pstmt.setObject(++num, category.getRefCategoryCode());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
        }

        return result;
    }

    /**
     *  Connection 객체를 전달받아 해당 객체의
     *  마지막 AutoIncrement 값을 반환하는 메소드
     * @param conn  - 사용할 Connection 객체
     * @return      - 최근에 생성된 CategoryCode
     */
    public int selectCurrentCategoryCode(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        int currentCategoryCode = 0;

        String query = prop.getProperty("selectCurrentCategoryCode");
        try {
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();
            if(rset.next()){
                currentCategoryCode = rset.getInt("curr_category_code");
            } // 맵핑
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }

        return currentCategoryCode;
    }
}
