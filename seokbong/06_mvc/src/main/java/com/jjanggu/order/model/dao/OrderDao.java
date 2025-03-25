package com.jjanggu.order.model.dao;

import com.jjanggu.menu.model.dto.MenuDto;
import com.jjanggu.order.model.dto.OrderDto;
import com.jjanggu.order.model.dto.OrderMenuDto;

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

public class OrderDao {

    private Properties prop = new Properties();

    public OrderDao(){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/jjanggu/mapper/order-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<OrderDto> selectOrderList(Connection conn) {

        List<OrderDto> list = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("selectOrderList");

        try {
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();

            while (rset.next()){
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

    public List<MenuDto> selectMenuByCategory(Connection conn, int categoryCode){

        // select문 => 여러행 => ResultSet => List<MenuDto>
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
                menu.setMenuPrice( rset.getInt("menu_price"));

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
        } finally{
            close(pstmt);
        }

        return result;

    }

    public int selectCurrOrderCode(Connection conn){
        // select문 => 한개의 숫자조회 => ResultSet => int
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
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(pstmt);
        }

        return currOrderCode;
    }

    public int insertOrderMenu(Connection conn, OrderMenuDto orderMenu){
        // insert => 삽입된 행 수 => int
        int result = 0;

        PreparedStatement pstmt = null;
        String query = prop.getProperty("insertOrderMenu");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, orderMenu.getOrderCode());
            pstmt.setInt(2,orderMenu.getMenuCode());
            pstmt.setInt(3,orderMenu.getOrderAmount());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
        }

        return result;
    }

    public List<OrderDto> selectOrderDetails(Connection conn, int orderCode){

        List<OrderDto> list = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("selectOrderDetails");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, orderCode);

            rset = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return list;
    }





}


