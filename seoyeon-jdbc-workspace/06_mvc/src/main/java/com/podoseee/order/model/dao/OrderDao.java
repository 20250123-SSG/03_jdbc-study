package com.podoseee.order.model.dao;

import com.podoseee.menu.model.dto.MenuDto;
import com.podoseee.order.model.dto.OrderDto;
import com.podoseee.order.model.dto.OrderMenuDto;

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

public class OrderDao {

    private Properties prop = new Properties();

    public OrderDao(){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/podoseee/mapper/order-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MenuDto> selectMenuByCategory(Connection conn, int categoryCode){

        // select문 => 여러행 => ResultSet => List<MenuDto>
        // 최종 담을 변수
        List<MenuDto> list = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("selectMenuByCategory");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryCode);

            rset = pstmt.executeQuery();

            while(rset.next()){
                MenuDto menu = new MenuDto();
                menu.setMenuCode( rset.getInt("menu_code") );
                menu.setMenuName( rset.getString("menu_name") );
                menu.setMenuPrice( rset.getInt("menu_price") );

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

    public int insertOrder(Connection conn, OrderDto order){
        // insert => 삽입된 행수 => int
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

    public int selectCurrOrderCode(Connection conn){
        // select문 => 한개의숫자조회 => ResultSet => int
        int currOrderCode = 0;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("selectCurrOrderCode");

        try {
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();

            if(rset.next()){
                currOrderCode = rset.getInt("curr_order_code");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return currOrderCode;
    }

    public int insertOrderMenu(Connection conn, OrderMenuDto orderMenu){
        // insert => 삽입된 행수 => int
        int result = 0;

        PreparedStatement pstmt = null;
        String query = prop.getProperty("insertOrderMenu");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, orderMenu.getOrderCode());
            pstmt.setInt(2, orderMenu.getMenuCode());
            pstmt.setInt(3, orderMenu.getOrderAmount());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            close(pstmt);
        }
        return result;
    }

    public List<OrderDto> selectAllOrder(Connection conn){
        // select => 여러행 => ResultSet => List<OrderDto>
        List<OrderDto> list = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("selectAllorder");

        try {
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();

            // 결과 매핑
            while(rset.next()){
                list.add(new OrderDto(
                        rset.getInt("order_code"),
                        rset.getString("order_date"),
                        rset.getString("order_time"),
                        rset.getInt("total_order_price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return list;

    }

    public List<OrderMenuDto> selectOrderDetail(Connection conn, int orderCode){
        // select문 => 여러행 => ResultSet => List<MenuDto>
        List<OrderMenuDto> list = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("selectOrderDetail");

        try {
            pstmt = conn.prepareStatement(query);
            // 파라미터 바인딩
            pstmt.setInt(1, orderCode);

            rset = pstmt.executeQuery();

            // 결과 매핑
            while(rset.next()){
                // 한 row에 대해 DTO의 필드에 매핑
                MenuDto menu = new MenuDto();
                menu.setMenuCode(rset.getInt("menu_code"));
                menu.setMenuName(rset.getString("menu_name"));
                menu.setMenuPrice(rset.getInt("menu_price"));
                menu.setCategory(rset.getString("category_name"));

                OrderMenuDto orderMenu = new OrderMenuDto();
                orderMenu.setOrderAmount(rset.getInt("order_amount"));
                orderMenu.setMenu(menu);

                list.add(orderMenu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return list;
    }

    public MenuDto selectMenuByName(Connection conn, String search){
        // select문 => 한 행 => ResultSet => MenuDto
        MenuDto menu = null;

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("selectMenuByName");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, search);
            rset = pstmt.executeQuery();

            if (rset.next()) {
                menu = new MenuDto();
                menu.setMenuCode(rset.getInt("menu_code"));
                menu.setMenuName(rset.getString("menu_name"));
                menu.setMenuPrice(rset.getInt("menu_price"));
                menu.setCategory(rset.getString("category_name"));
        } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close(rset);
                close(pstmt);
            }
            return menu;


    }

}
