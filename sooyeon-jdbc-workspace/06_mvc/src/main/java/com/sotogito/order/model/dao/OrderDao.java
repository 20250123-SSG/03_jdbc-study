package com.sotogito.order.model.dao;

import com.sotogito.menu.model.dto.MenuDto;
import com.sotogito.order.model.dto.OrderDto;
import com.sotogito.order.model.dto.OrderMenuDto;

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

public class OrderDao {
    private final static String ORDER_QUERY_XML_PATH = "src/main/java/com/sotogito/mapper/order-query.xml";
    private final Properties prop = new Properties();

    public OrderDao() {
        try {
            prop.loadFromXML(new FileInputStream(ORDER_QUERY_XML_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MenuDto> selectMenuByCategory(Connection conn, int categoryCode) {
        List<MenuDto> result = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = prop.getProperty("selectMenuByCategory");
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, categoryCode); ///추가도하고 받아도와야됨
            rs = ps.executeQuery();

            while (rs.next()) {
                MenuDto menu = new MenuDto();
                menu.setMenuCode(rs.getInt("menu_code"));
                menu.setMenuName(rs.getString("menu_name"));
                menu.setMenuPrice(rs.getInt("menu_price"));

                result.add(menu);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(ps);
        }

        return result;
    }

    public int insertOrder(Connection conn, OrderDto order) {
        int result = 0;

        PreparedStatement ps = null;

        String query = prop.getProperty("insertOrder");
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, order.getTotalOrderPrice());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(ps);
        }

        return result;
    }

    public int selectCurrOrderCode(Connection conn) {
        int result = 0;

        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = prop.getProperty("selectCurrOrderCode");
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getInt("curr_order_code");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(ps);
        }

        return result;
    }

    public int insertOrderMenu(Connection conn, OrderMenuDto orderMenu) {
        int result = 0;

        PreparedStatement ps = null;

        String query = prop.getProperty("insertOrderMenu");
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, orderMenu.getOrderCode());
            ps.setInt(2, orderMenu.getMenuCode());
            ps.setInt(3, orderMenu.getOrderAmount());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(ps);
        }

        return result;
    }


    /*
    public List<OrderMenuDto> selectOrderMenu(Connection conn, OrderDto order) {
        List<OrderMenuDto> result = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = prop.getProperty("selectOrderMenu");
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, order.getOrderCode()); ///주문 코드와 일치하는 주문 정보 가져오기
            rs = ps.executeQuery();

            while (rs.next()) {
                result.add(new OrderMenuDto(
                        rs.getInt("order_code")
                        , rs.getInt("menu_code")
                        , rs.getInt("order_amount")
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

     */

    public List<OrderMenuDto> selectMenuByOrderCode(Connection conn, int orderCode) {
        List<OrderMenuDto> result = new ArrayList<>(); ///하나의 주문에 여러 메뉴 포함

        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = prop.getProperty("selectMenuByOrderCode");
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1,orderCode);
            rs = ps.executeQuery();

            while (rs.next()) {
                OrderMenuDto orderMenu = new OrderMenuDto();
                orderMenu.setOrderAmount(rs.getInt("order_amount"));
                orderMenu.setMenu(new MenuDto(
                        rs.getInt("menu_code")
                        , rs.getString("menu_name")
                        , rs.getInt("menu_price")
                        , rs.getString("category_name")
                        , rs.getString("orderable_status")));

                result.add(orderMenu);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(ps);
        }

        return result;
    }

    public List<OrderDto> selectAllOrder(Connection conn) {
        List<OrderDto> reuslt = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = prop.getProperty("selectAllOrder");
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                OrderDto order = new OrderDto();
                order.setOrderCode(rs.getInt("order_code"));
                order.setOrderDate(rs.getString("order_date"));
                order.setOrderTime(rs.getString("order_time"));
                order.setTotalOrderPrice(rs.getInt("total_order_price"));

                reuslt.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(ps);
        }

        return reuslt;
    }

//    public MenuDto selectOrderMenuDetails(Connection conn, OrderMenuDto orderMenuDto) {    >>>>>>>>>> MenuDao로 옮김
//        MenuDto result = new MenuDto();
//
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        String query = orderProp.getProperty("selectOrderMenuDetails");
//
//        try {
//            ps = conn.prepareStatement(query);
//            ps.setInt(1,orderMenuDto.getMenuCode());
//            rs = ps.executeQuery();
//
//            if (rs.next()) {
//                result.setMenuCode(rs.getInt("menu_code"));
//                result.setMenuName(rs.getString("menu_name"));
//                result.setMenuPrice(rs.getInt("menu_price"));
//                result.setCategory(rs.getString("category_name"));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }finally {
//            close(rs);
//            close(ps);
//        }
//        return result;
//    }
}
