package com.sotogito.model.dao;

import com.sotogito.model.dto.CategoryDTO;
import com.sotogito.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.sotogito.common.JDBCTemplate.close;
/**
 * ## DAO
 * 1. Data Access Object
 * 2. 데이터베이스 접근용 객체
 * 3. CRUD 연산을 담강하는 메서드들의 집합으로 이루어진 클래스
 * 4. 실행할 sql문 별로 메서드로 따로 구성
 */

/**
 * - 마지막 메뉴 번호 조회용
 * - 카테고리 전체 정보 조회용
 * - 메뉴 등록용
 */
public class MenuDAO {
    String xmlPath = "src/main/java/com/sotogito/mapper/menu-query.xml";
    private Properties prop = new Properties();


    public MenuDAO() {
        try {
            prop.loadFromXML(new FileInputStream(xmlPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 현존하는 메뉴들 중에 가장 마지막 메뉴 번로 조회해주는 코드
     *
     * @param conn : 왜 주입??????????????재사용 때문에, 하나의 DB 연결로 여러 DAO 메서드를 실행할 수 있다
     * @return : 조회된 마지막 메뉴 번호
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
                System.out.println(lastMenuCode);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(ps);
        }
        return lastMenuCode;
    }


    /**
     * 현존하는 카테고리의 전체 정보를 조회해주는 메서드
     * @param conn
     * @return : 조회 경과가 담겨있는 리스트 반환
     */
    public List<CategoryDTO> selectCategoryList(Connection conn) {
        List<CategoryDTO> result = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = prop.getProperty("selectCategoryList");

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                CategoryDTO categoryDto = new CategoryDTO();
                categoryDto.setCategoryCode(rs.getInt("category_code"));
                categoryDto.setCategoryName(rs.getString("category_name"));

                result.add(categoryDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(ps);
        }
        return result;
    }


    /**
     * 신규 메뉴룰 등록해주는 메서드
     * @param conn : db와 연결된 Connection 객체
     * @param menuDto : 원래 모든 값들을 매개변수로 받아야하는데 DTO를 활용하여 get해옴 : 코드 깔끔
     * @return : 처리된 행 수 반환
     */
    public int insertMenu(Connection conn, MenuDTO menuDto) {
        PreparedStatement ps = null;
        int insertResult = 0;

        String insertQuery = prop.getProperty("insertMenu");
        try {
            ps = conn.prepareStatement(insertQuery);
            ps.setInt(1, menuDto.getMenuCode());
            ps.setString(2, menuDto.getMenuName());
            ps.setInt(3, menuDto.getMenuPrice());
            ps.setInt(4, menuDto.getCategoryCode());
            ps.setString(5, menuDto.getOrderableStatus());

            insertResult = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(ps);
            close(conn);
        }
        return insertResult;
    }

}
