package com.podoseee.order.controller;

import com.podoseee.menu.model.dto.MenuDto;
import com.podoseee.order.model.dto.OrderDto;
import com.podoseee.order.model.dto.OrderMenuDto;
import com.podoseee.order.model.service.OrderService;
import com.podoseee.order.view.PrintResultView;

import java.util.List;
import java.util.Map;

public class OrderController {

    private OrderService orderService = new OrderService();
    private PrintResultView printResultView = new PrintResultView();

    public List<MenuDto> selectOrderableMenuList(String category){
        int categoryCode = Integer.parseInt(category);
        List<MenuDto> list = orderService.selectOrderableMenuList(categoryCode);
        return list;
    }

    public void registOrder(Map<String, Object> requestParam) {
        // 요청 시 전달값 뽑기
        List<OrderMenuDto> orderMenuList = (List<OrderMenuDto>)requestParam.get("orderMenuList"); // 다운캐스팅(object를 list로)
        int totalPrice = (int)requestParam.get("totalPrice");

        // DTO(OrderDto)에 담기
        OrderDto order = new OrderDto(); // { }
        order.setTotalOrderPrice(totalPrice); // { totalOrderPrice:xxxx }
        order.setOrderMenuList(orderMenuList); // { totalOrderPrice:xxxx, orderMenuList<OrderMenuDto> 객체 }

        orderService.registOrder(order);
    }
}
