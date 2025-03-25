package com.kyungbae.order.model.dao;

import com.kyungbae.menu.model.dto.MenuDto;
import com.kyungbae.order.model.dto.OrderDto;
import com.kyungbae.order.model.dto.OrderMenuDto;

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

public class OrderDao {
    private Properties prop = new Properties();

    public OrderDao(){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/kyungbae/mapper/order-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * categoryCode를 통하여 판매가능한 메뉴를 조회하는 메소드
     * @param conn          - 조회할 Connection 객체
     * @param categoryCode  - 조회할 categoryCode
     * @return              - 조회된 List<MenuDto> 값
     */
    public List<MenuDto> selectMenuByCategory(Connection conn, int categoryCode) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<MenuDto> menuList = new ArrayList<>();

        String query = prop.getProperty("selectMenuByCategory");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryCode);
            rset = pstmt.executeQuery();

            while (rset.next()){
                MenuDto menu = new MenuDto();
                menu.setMenuCode(rset.getInt("menu_code"));
                menu.setMenuName(rset.getString("menu_name"));
                menu.setMenuPrice(rset.getInt("menu_price"));

                menuList.add(menu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return menuList;
    }

    /**
     *  주문한 총 금액을 현재시간과 함께 tbl_order에 등록하는 메소드
     * @param conn  - 설정을 진행할 Connection객체
     * @param order - totalOrderPrice값이 담긴 OrderDto
     * @return      - 입력 결과 출력
     */
    public int insertOrder(Connection conn, OrderDto order) {
        int result = 0;
        PreparedStatement pstmt = null;
        String query = prop.getProperty("insertOrder");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, order.getTotalOrderPrice());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }

        return result;
    }

    public int selectCurrOrderCode(Connection conn) {
        int currOrderCode = 0;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("selectCurrOrderCode");

        try {
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();

            if (rset.next()) {
                currOrderCode = rset.getInt("curr_order_code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }

        return currOrderCode;
    }

    public int insertOrderMenu(Connection conn, OrderMenuDto orderMenu) {
        int result = 0;
        PreparedStatement pstmt = null;
        String query = prop.getProperty("insertOrderMenu");
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, orderMenu.getOrderCode());
            pstmt.setInt(2, orderMenu.getMenuCode());
            pstmt.setInt(3, orderMenu.getOrderAmount());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }

    public List<OrderDto> getOrderTable(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<OrderDto> list = new ArrayList<>();
        String query = prop.getProperty("getOrderTable");

        try {
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                OrderDto orderDto = new OrderDto(
                        rset.getInt("order_code"),
                        rset.getString("order_date"),
                        rset.getString("order_time"),
                        rset.getInt("total_order_price")
                );
                list.add(orderDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return list;
    }

    public List<MenuDto> retrieveOrderMenu(Connection conn, int orderCode) {
        List<MenuDto> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("retrieveDetailOrder");
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, orderCode);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                MenuDto menu = new MenuDto();
                menu.setMenuCode(rset.getInt("menu_code"));
                menu.setMenuName(rset.getString("menu_name"));
                menu.setMenuPrice(rset.getInt("menu_price"));
                menu.setCategory(rset.getString("category_name"));

                list.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return list;
    }
}
