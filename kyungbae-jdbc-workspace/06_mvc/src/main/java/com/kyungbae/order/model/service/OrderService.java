package com.kyungbae.order.model.service;

import com.kyungbae.menu.model.dto.MenuDto;
import com.kyungbae.order.model.dao.OrderDao;
import com.kyungbae.order.model.dto.OrderDto;
import com.kyungbae.order.model.dto.OrderMenuDto;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.kyungbae.common.JDBCTemplate.*;

public class OrderService {
    private OrderDao orderDao = new OrderDao();

    public List<MenuDto> selectOrderableMenuList(int categoryCode) {
        Connection conn = getConnection();
        List<MenuDto> menuList = orderDao.selectMenuByCategory(conn, categoryCode);
        close(conn);
        return menuList;
    }

    public int registOrder(OrderDto order) {
        int result = 0;

        Connection conn = getConnection();
        // 1. tbl_order 테이블에 주문정보 1행 insert (부모 테이블에 먼저 삽입)
        int result1 = orderDao.insertOrder(conn, order);

        // 2. 1번 과정에 의해서 발생된 주문번호 조회
        int currOrderCode = orderDao.selectCurrOrderCode(conn);

        // 3. tbl_order_menu 테이블에 주문메뉴수만큼 반복적으로 insert
        List<OrderMenuDto> list = order.getOrderMenuList();
        int result2 = 0; // 총 삽입될 행 수
        for (OrderMenuDto orderMenu : list) {
            orderMenu.setOrderCode(currOrderCode);
            result2 += orderDao.insertOrderMenu(conn, orderMenu);
        }
        if (list.size() == result2 && result1 > 0) {
            commit(conn);
            result = 1;
        } else {
            rollback(conn);
        }
        close(conn);

        return result;
    }

    public List<OrderDto> selectOrder() {
        Connection conn = getConnection();
        List<OrderDto> list = orderDao.getOrderTable(conn);
        close(conn);
        return list;
    }

    public List<MenuDto> retrieveOrderMenu(int orderCode) {
        Connection conn = getConnection();
        List<MenuDto> list = new ArrayList<>();
        list = orderDao.retrieveOrderMenu(conn, orderCode);
        close(conn);
        return list;
    }

    public MenuDto selectMenuByName(String search) {
        Connection conn = getConnection();
        MenuDto menu = orderDao.selectMenuByName(conn, search);
        close(conn);
        return menu;
    }

}
