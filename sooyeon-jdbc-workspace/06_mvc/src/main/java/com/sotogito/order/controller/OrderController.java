package com.sotogito.order.controller;

import com.sotogito.menu.model.dto.MenuDto;
import com.sotogito.order.OrderFunction;
import com.sotogito.order.model.dto.OrderDto;
import com.sotogito.order.model.dto.OrderMenuDto;
import com.sotogito.order.model.service.OrderService;
import com.sotogito.order.view.PrintResultView;

import java.util.List;
import java.util.Map;

public class OrderController {
    private final PrintResultView printResultView = new PrintResultView();
    private final OrderService orderService = new OrderService();

    public List<MenuDto> selectOrderableMenuList(String category) {
        int categoryCode = Integer.parseInt(category);

        return orderService.selectOrderableMenuList(categoryCode);
    }

    public List<MenuDto> selectOrderMenuDetailList(String orderCode) {
        OrderDto order = new OrderDto();
        order.setOrderCode(Integer.parseInt(orderCode));

        return orderService.selectOrderMenuDetails(order);
    }

    public MenuDto selectMenuByMenuName(String menuName) {
        MenuDto menuDto = new MenuDto();
        menuDto.setMenuName(menuName);

        return orderService.selectMenuByMenuName(menuDto);
    }

    public void registOrder(Map<String, Object> requestParams) {
        int totalOrderPrice = (int) requestParams.get("totalOrderPrice");
        List<OrderMenuDto> orderMenuList = (List<OrderMenuDto>) requestParams.get("orderMenuList");

        OrderDto order = new OrderDto();
        order.setTotalOrderPrice(totalOrderPrice);
        order.setOrderMenuList(orderMenuList);

        int insertResult = orderService.registOrder(order);

        if (insertResult == 1) {
            printResultView.displaySuccessMessage(OrderFunction.ORDER);
        } else {
            printResultView.displayFailMessage(OrderFunction.ORDER);
        }
    }

}
