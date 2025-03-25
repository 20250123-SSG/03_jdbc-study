package com.sotogito.order.model.service;

import com.sotogito.menu.model.dao.MenuDao;
import com.sotogito.menu.model.dto.MenuDto;
import com.sotogito.order.model.dao.OrderDao;
import com.sotogito.order.model.dto.OrderDto;
import com.sotogito.order.model.dto.OrderMenuDto;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sotogito.common.JDBCTemplate.*;

public class OrderService {
    private final OrderDao orderDao = new OrderDao();
    private final MenuDao menuDao = new MenuDao();

    public List<MenuDto> selectOrderableMenuList(int categoryCode) {
        Connection conn = getConnection();

        List<MenuDto> orderableMenuList = orderDao.selectMenuByCategory(conn, categoryCode);

        if (orderableMenuList == null || orderableMenuList.isEmpty()) {
            throw new IllegalArgumentException("업다");
        }
        close(conn);
        return orderableMenuList;
    }

    /**
     * 1. tbl_order 테이블에 주문 정보 1행 insert -> 주문번호 생성
     * 2. 1번 과정에서 생성된 주문번호 조회
     * 3. tbl_order_menu 테이블에 주문메뉴수만큼 반복적으로 insert
     */
    public int registOrder(OrderDto order) {
        int result = 0;

        Connection conn = getConnection();

        int insertOrderResult = orderDao.insertOrder(conn, order);
        if (insertOrderResult != 1) {
            rollback(conn);
            return result;
        }

        int currOrderCode = orderDao.selectCurrOrderCode(conn);

        List<OrderMenuDto> orderMenuList = order.getOrderMenuList();
        int insertOrderMenuResult = 0;

        for (OrderMenuDto orderMenu : orderMenuList) {
            orderMenu.setOrderCode(currOrderCode);
            insertOrderMenuResult += orderDao.insertOrderMenu(conn, orderMenu);
        }

        if (orderMenuList.size() == insertOrderMenuResult) {
            result = 1;
            commit(conn);
        } else {
            rollback(conn);
        }

        close(conn);
        return result;
    }

    /**
     * 1. orderCode와 일치하는 주문 정보 가져오기(메뉴 번호가 포함된) FROM tbl_order_menu                    -> OrderDao
     * 2. List<OrderMenuDto>의 메뉴코드를 활용하여 tbl_menu에서 메뉴 코드와 일치하는 메뉴 데이터 가져오기     -> MenuDao
     *
     * @param order
     * @return
     */
    public List<MenuDto> selectOrderMenuDetails(OrderDto order) {
        List<MenuDto> result = new ArrayList<>();

        Connection conn = getConnection();

        /// 1. 주문 코드로 메뉴 번호가 담겨있는 데이터 가져오기 FROM tbl_order_menu
        List<OrderMenuDto> orderMenuList = orderDao.selectOrderMenu(conn, order);
        if (orderMenuList == null || orderMenuList.isEmpty()) {
            rollback(conn);
            throw new IllegalArgumentException("주문 기록이 없습니다.");
        }

        /// 2. 주문 코드에 따른 메뉴 코드로 메뉴db에 접근하여 데이터를 가져온다.
        for (OrderMenuDto orderMenu : orderMenuList) {
            Optional<MenuDto> menu = menuDao.selectMenuByMenuCode(conn, orderMenu);  /// 메뉴 번호와 일치하는 메뉴 데이터를 가져오는 것은 MenuDao의 역할
            if (menu.isEmpty()) {
                throw new IllegalArgumentException("현재는 존재하지 않는 메뉴입니다.");
            }
            result.add(menu.get());
        }

        if (orderMenuList.size() == result.size()) {
            commit(conn);
        } else {
            rollback(conn);
        }

        close(conn);
        return result;
    }

    public MenuDto selectMenuByMenuName(MenuDto menu) {
        Connection conn = getConnection();

        Optional<MenuDto> result = menuDao.selectMenuByMenuName(conn, menu);
        if (result.isEmpty()) {
            rollback(conn);
            throw new IllegalArgumentException("존재하지 않는 메뉴입니다.");
        } else {
            commit(conn);
        }

        close(conn);
        return result.get();
    }

}
