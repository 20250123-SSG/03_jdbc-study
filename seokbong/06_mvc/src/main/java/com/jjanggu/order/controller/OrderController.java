package com.jjanggu.order.controller;

import com.jjanggu.menu.model.dto.MenuDto;

import com.jjanggu.order.model.dto.OrderDto;
import com.jjanggu.order.model.dto.OrderMenuDto;
import com.jjanggu.order.model.service.OrderService;
import com.jjanggu.order.view.PrintResultView;

import java.util.List;
import java.util.Map;

public class OrderController {

    private OrderService orderService = new OrderService();
    private PrintResultView printResultView = new PrintResultView();

    public List<MenuDto> selectOrderablMenuList(String category){
        int categoryCode = Integer.parseInt(category);
        List<MenuDto> list = orderService.selectOrderableMenuList(categoryCode);
        return list;
    }

    public void registOrder(Map<String, Object> requestParam){
        // 요청시 전달값 뽑기
        List<OrderMenuDto> orderMenuList = (List<OrderMenuDto>)requestParam.get("orderMenuList");
        int totalPrice = (int)requestParam.get("totalPrice");

        // DTO(OrderDto)에 담기
        OrderDto order = new OrderDto(); // {}
        order.setTotalOrderPrice(totalPrice); // { totalOrderPrice:xxxxx }
        order.setOrderMenuList(orderMenuList); // { totalOrderPrice:xxxxx, orderMenuList:List<OrderMenuDto>객체 }

        int result = orderService.registOrder(order);

        if(result > 0){
            printResultView.displaySuccessMessage("order");
        }else {
            printResultView.displayFailMessage("order");
        }

    }

    public List<OrderDto> selectOrderList (){
        List<OrderDto> list = orderService.selectOrderList();
        return list;
    }

    public List<OrderDto> selectOrderDetails (String orderNum){
        int orderCode = Integer.parseInt(orderNum);
        List<OrderDto> list = orderService.selectOrderDetails(orderCode);
        return list;
    }

}
