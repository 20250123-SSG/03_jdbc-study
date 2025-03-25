package com.sotogito.menu.model.dao;

import com.sotogito.menu.model.dto.CategoryDto;
import com.sotogito.menu.model.dto.MenuDto;
import com.sotogito.order.model.dto.OrderMenuDto;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static com.sotogito.common.JDBCTemplate.close;

public class MenuDao {
    private final static String xmlPath = "src/main/java/com/sotogito/mapper/menu-query.xml";
    private Properties prop = new Properties();

    public MenuDao() {
        try {
            prop.loadFromXML(new FileInputStream(xmlPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MenuDto> selectAllMenu(Connection conn) {
        List<MenuDto> result = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = prop.getProperty("selectAllMenu");
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                result.add(new MenuDto(
                        rs.getInt("menu_code")
                        , rs.getString("menu_name")
                        , rs.getInt("menu_price")
                        , rs.getString("category_name")
                        , rs.getString("orderable_status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
        }
        return result;
    }

    public List<CategoryDto> selectAllCategory(Connection conn) {
        List<CategoryDto> result = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = prop.getProperty("selectAllCategory");
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                result.add(new CategoryDto(
                        rs.getInt("category_code")
                        , rs.getString("category_name")
                        , rs.getInt("ref_category_code")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(ps);
        }

        return result;
    }


    public int registMenu(Connection conn, MenuDto menu) {
        int result = 0;
        PreparedStatement ps = null;

        String query = prop.getProperty("insertMenu");
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, menu.getMenuName());
            ps.setInt(2, menu.getMenuPrice());
            ps.setInt(3, Integer.parseInt(menu.getCategory()));
            ps.setString(4, menu.getOrderableStatus());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
        }

        return result;
    }

    public int updateMenu(Connection conn, MenuDto menu) {
        int result = 0;

        PreparedStatement ps = null;

        String query = prop.getProperty("updateMenu");
        /**
         * 삭제할 메뉴코드 데이터와
         * 삭제 후 수정할 데이터가
         * 같은 dto로 전달되는 것이 맞나?
         *
         * 어차피 수정 후 데이터는 삭제할ID를 동일하게 갖는다.
         * 즉 dto에 전달된 데이터 그대로 사용되기 떄문에 무방하다.
         *
         */
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, menu.getMenuName());
            ps.setInt(2, menu.getMenuPrice());
            ps.setInt(3, Integer.parseInt(menu.getCategory()));
            ps.setString(4, menu.getOrderableStatus());

            ps.setInt(5, menu.getMenuCode());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(ps);
        }

        return result;
    }

    public int deleteMenu(Connection conn, int menuCode) {
        int result = 0;

        PreparedStatement ps = null;

        String query = prop.getProperty("deleteMenu");
        /**
         * 메뉴코드로 삭제한다면 MenuDto로 넘기는 것이 좋나
         * 아니면 그냥 INT 로받아서 처리하는게 좋나>?
         * id는 PK이기 때문에 삭제할 메뉴가 무엇인지 명확하게 파악이 가능하다. 고로 그냥 int로 넘겨 삭제
         */
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, menuCode);

            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(ps);
        }

        return result;
    }





    public Optional<MenuDto> selectMenuByMenuCode(Connection conn, OrderMenuDto orderMenuDto) {
        MenuDto result = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = prop.getProperty("selectMenuByMenuCode");

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, orderMenuDto.getMenuCode());
            rs = ps.executeQuery();

            if (rs.next()) {
                result = new MenuDto();

                result.setMenuCode(rs.getInt("menu_code"));
                result.setMenuName(rs.getString("menu_name"));
                result.setMenuPrice(rs.getInt("menu_price"));
                result.setCategory(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(ps);
        }
        return Optional.ofNullable(result);
    }

    public Optional<MenuDto> selectMenuByMenuName(Connection conn, MenuDto menuDto) {
        MenuDto result = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = prop.getProperty("selectMenuByMenuName");
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, menuDto.getMenuName());
            rs = ps.executeQuery();

            if (rs.next()) {
                result = new MenuDto(
                        rs.getInt("menu_code")
                        , rs.getString("menu_name")
                        , rs.getInt("menu_price")
                        , rs.getString("category_name")
                        , rs.getString("orderable_status")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(ps);
        }
        return Optional.ofNullable(result);
    }

}
