package com.inyong.order.controller;

import com.inyong.menu.model.dto.MenuDto;
import com.inyong.order.model.dto.OrderDto;
import com.inyong.order.model.dto.OrderMenuDto;
import com.inyong.order.model.service.OrderService;
import com.inyong.order.view.PrintResultView;

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

    public void registOrder(Map<String, Object> requestParam){
        // 요청시 전달값 뽑기
        List<OrderMenuDto> orderMenuList = (List<OrderMenuDto>)requestParam.get("orderMenuList");
        int totalPrice = (int)requestParam.get("totalPrice");

        // DTO(OrderDto)에 담기
        OrderDto order = new OrderDto(); // {  }
        order.setTotalOrderPrice(totalPrice); // { totalOrderPrice:xxxxx }
        order.setOrderMenuList(orderMenuList); // { totalOrderPrice:xxxxx, orderMenuList:List<OrderMenuDto>객체 }

        orderService.registOrder(order);


    }

}
