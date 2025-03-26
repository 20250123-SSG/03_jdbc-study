package com.ibe6.order.service;
import com.ibe6.menu.model.dto.MenuDto;
import com.ibe6.order.model.dao.OrderDao;
import com.ibe6.order.model.dto.OrderDto;
import com.ibe6.order.model.dto.OrderMenuDto;

import java.sql.Connection;
import java.util.List;

import static com.ibe6.common.JDBCTemplate.*;

public class OrderService {

    private OrderDao orderDao = new OrderDao();

    public List<MenuDto> selectOrderableMenuList(int categoryCode){
        Connection conn = getConnection();
        List<MenuDto> list = orderDao.selectMenuByCategory(conn, categoryCode);
        close(conn);
        return list;
    }

    public int registOrder(OrderDto order){ // 총주문가격과 주문메뉴(메뉴번호,수량)목록이 담겨있는 OrderDto객체

        int result = 0; // 모든 작업의 최종 결과
        Connection conn = getConnection();

        // 1. tbl_order 테이블에 주문정보 1행 insert => 주문번호 생성
        int result1 = orderDao.insertOrder(conn, order);

        // 2. 1번 과정에 의해서 발생된 주문번호 조회
        int currOrderCode = orderDao.selectCurrOrderCode(conn);

        // 3. tbl_order_menu 테이블에 주문메뉴수만큼 반복적으로 insert
        List<OrderMenuDto> list = order.getOrderMenuList(); // list.size() == insert할 행수
        int result2 = 0; // 총 삽입된 행수를 기록할 변수
        for(OrderMenuDto orderMenu : list){ // orderMenu {menuCode:xx. orderAmount: xx}
            orderMenu.setOrderCode(currOrderCode); // {menuCode:xx. orderAmount: xx, orderCode: xxx}
            result2 += orderDao.insertOrderMenu(conn, orderMenu);
        }

        if(result1 > 0 && result2 == list.size()){
            commit(conn);
            result = 1;
        }else{
            rollback(conn);
        }return result;

    }

    public List<OrderDto> selectAllOrder(){
        Connection conn = getConnection();
        List<OrderDto> orderList = orderDao.selectAllOrder(conn);
        close(conn);
        return orderList;
    }
}
