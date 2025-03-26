package com.younggalee.order.model.dao;

import com.younggalee.menu.model.dto.MenuDto;
import com.younggalee.order.model.dto.OrderDto;
import com.younggalee.order.model.dto.OrderMenuDto;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.younggalee.common.JDBCTemplate.close;

public class OrderDao {
    private Properties prop = new Properties();
    public OrderDao() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/younggalee/mapper/order-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MenuDto> selectMenuByCategory (Connection conn, int categoryCode) {
        // select문 >> 여러행 > ResultSet > List<MenuDto>
        // menu dto에 담을 거고 리스트에 담을 거임
        List<MenuDto> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("selectMenuByCategory");

        //생성
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryCode);

            rset = pstmt.executeQuery();

            while (rset.next()) {
                // 커서 하나씩 옮겨서 dto객체에 담을 것임
                MenuDto menu = new MenuDto(); // 기본으로 생성하고
                //setter로 담음
                menu.setMenuCode(rset.getInt("menu_code"));
                menu.setMenuName(rset.getString("menu_name"));
                menu.setMenuPrice(rset.getInt("menu_price"));

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

    public int insertOrder (Connection conn, OrderDto order) {
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
        //select문 > 한개의 숫자조회 > ResultSet > int
        int currOrderCode = 0;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("selectCurrOrderCode");

        try {
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();
            if(rset.next()) {
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

    public int insertOrderMenu(Connection conn, OrderMenuDto orderMenu){
        // insert >> 삽입된 행수 >> int
        int result = 0;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("insertOrderMenu");

        try {
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, orderMenu.getOrderCode());
            pstmt.setInt(2, orderMenu.getMenuCode());
            pstmt.setInt(3, orderMenu.getOrderAmount());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        } finally{
            close(pstmt);
        }


        return result;

    }

}
