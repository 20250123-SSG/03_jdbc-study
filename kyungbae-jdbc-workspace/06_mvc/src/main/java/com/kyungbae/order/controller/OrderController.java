package com.kyungbae.order.controller;

import com.kyungbae.menu.model.dto.MenuDto;
import com.kyungbae.order.model.dto.OrderDto;
import com.kyungbae.order.model.dto.OrderMenuDto;
import com.kyungbae.order.model.service.OrderService;
import com.kyungbae.order.view.PrintResultView;

import java.util.List;
import java.util.Map;

public class OrderController {
    private OrderService orderService = new OrderService();
    private PrintResultView printResultView = new PrintResultView();

    public List<MenuDto> selectOrderableMenuList(String category) {
        int categoryCode = Integer.parseInt(category);
        List<MenuDto> menuList = orderService.selectOrderableMenuList(categoryCode);
        return menuList;
    }

    public void registOrder(Map<String, Object> requestParam) {
        List<OrderMenuDto> orderMenuList = (List<OrderMenuDto>)requestParam.get("orderMenuList");
        int totalPrice = (int)requestParam.get("totalPrice");

        // DTO에 담기
        OrderDto order = new OrderDto();
        order.setTotalOrderPrice(totalPrice);
        order.setOrderMenuList(orderMenuList);

        int result = orderService.registOrder(order);
        if (result > 0) {
            printResultView.displaySuccessMessage("order");
        } else {
            printResultView.displayFailMessage("order");
        }
    }

    public List<OrderDto> selectOrder() {
        List<OrderDto> list = orderService.selectOrder();
        return list;
    }

    public List<MenuDto> retrieveOrderMenu(int orderCode) {
        List<MenuDto> list = orderService.retrieveOrderMenu(orderCode);
        return list;
    }
}
