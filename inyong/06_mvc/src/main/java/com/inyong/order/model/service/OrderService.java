package com.inyong.order.model.service;

import com.inyong.menu.model.dto.MenuDto;
import com.inyong.order.model.dao.OrderDao;
import com.inyong.order.model.dto.OrderDto;

import java.sql.Connection;
import java.util.List;

import static com.inyong.common.JDBCTemplate.close;
import static com.inyong.common.JDBCTemplate.getConnection;

public class OrderService {

    private OrderDao orderDao = new OrderDao();

    public List<MenuDto> selectOrderableMenuList(int categoryCode){
        Connection conn = getConnection();
        List<MenuDto> list = orderDao.selectMenuByCategory(conn, categoryCode);
        close(conn);
        return list;
    }

    public void registOrder(OrderDto order){ // 총주문가격과 주문메뉴(메뉴번호,수량)목록이 담겨있는 OrderDto 객체

        // 1. tbl_order 테이블에 주문정보 1행 insert


    }

}
