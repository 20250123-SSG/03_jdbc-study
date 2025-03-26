package com.younggalee.order.model.service;

import com.younggalee.menu.model.dto.MenuDto;
import com.younggalee.order.model.dao.OrderDao;
import com.younggalee.order.model.dto.OrderDto;
import com.younggalee.order.model.dto.OrderMenuDto;
import com.younggalee.order.view.PrintResultView;

import java.sql.Connection;
import java.util.List;

import static com.younggalee.common.JDBCTemplate.*;

public class OrderService {

    private OrderDao orderDao = new OrderDao();

    public List<MenuDto> selectOrderableMenuList(int categoryCode){
        // 조회
        Connection conn = getConnection();
        // 카테고리 번호로 메뉴 조회하는 메소드
        List<MenuDto> list = orderDao.selectMenuByCategory(conn, categoryCode);
        close(conn);
        return list;
    }


    //총 주문가격과 주문내용(메뉴번호, 수량)목록이 담겨있는 orderdto객체
    public int registOrder(OrderDto order){
        int result = 0; // 모든 작업의 최종 결과
        Connection conn = getConnection();
        // 이게 하나의 트렌젝션
        // 1. tbl_order 테이블에 주문정보 1행 insert >> 주문번호 생성
        int result1 = orderDao.insertOrder(conn, order);
        // 2. 1번 과정에 의해서 발생된 주문번호 조회
        int currOrderCode = orderDao.selectCurrOrderCode(conn);
        // 3. tbl_order_menu 테이블에 주문메뉴수만큼 반복적으로 insert
        List<OrderMenuDto> list = order.getOrderMenuList();
        int result2 = 0; // 총 삽입된 행수를 기록할 변수
        for (OrderMenuDto orderMenu : list){ // 주문번호 없으니까
            orderMenu.setOrderCode(currOrderCode); // 추가해주고
            result2 += orderDao.insertOrderMenu(conn, orderMenu); // 인서트해줌
        }
        if (result1 > 0 && result2 == list.size()){
            commit(conn);
            result =1;
        } else {
            rollback(conn);
        }
        return result;


    }
}
