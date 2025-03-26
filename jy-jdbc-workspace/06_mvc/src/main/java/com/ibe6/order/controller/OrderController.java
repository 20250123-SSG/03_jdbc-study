package com.ibe6.order.controller;

import com.ibe6.menu.model.dto.MenuDto;
import com.ibe6.order.model.dto.OrderDto;
import com.ibe6.order.model.dto.OrderMenuDto;
import com.ibe6.order.service.OrderService;
import com.ibe6.order.view.PrintResultView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ibe6.common.JDBCTemplate.close;

public class OrderController {

    private OrderService orderService = new OrderService();
    private PrintResultView printResultView = new PrintResultView();

    public List<MenuDto> selectOrderableMenuList(String category) {
        int categoryCode = Integer.parseInt(category);
        List<MenuDto> list = orderService.selectOrderableMenuList(categoryCode);
        return list;
    }

    public void registOrder(Map<String, Object> requestParam) {
        // 요청시 전달값 뽑기
        List<OrderMenuDto> orderMenuList = (List<OrderMenuDto>) requestParam.get("orderMenuList");
        int totalPrice = (int) requestParam.get("totalPrice");

        // DTO(OrderDTO)에 담기
        OrderDto order = new OrderDto();       // { }
        order.setTotalOrderPrice(totalPrice);  // { totalOrderPrice:xxxxx }
        order.setOrderMenuList(orderMenuList); // { totalOrderPrice:xxxxx, orderMenuList<OrderMenuDto> 객체 }

        int result = orderService.registOrder(order);
        if (result > 0) {
            printResultView.displaySuccessMessage("order");
        } else {
            printResultView.displayFailMessage("order");
        }
    }


    public List<OrderDto> selectAllOrder() {
        return orderService.selectAllOrder();
    }



    /*
    public List<OrderMenuDto> selectOrderMenuByOrderCode(Connection conn, int orderCode){
        List<OrderMenuDto> menuList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("selectOrderMenuByOrderCode");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, orderCode);
            rset = pstmt.executeQuery();

            while (rset.next()){
                OrderMenuDto menu = new OrderMenuDto();
                menu.setOrderCode(rset.getInt("order_code"));
                menu.setMenuCode(rset.getInt("menu_code"));
                menu.setOrderAmount(rset.getInt("order_amount"));

                menuList.add(menu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        } return menuList;
    }

     */
}
