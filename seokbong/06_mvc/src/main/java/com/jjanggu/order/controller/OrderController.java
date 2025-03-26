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

    public List<OrderMenuDto> selectOrderDetails (String Code){
        int orderCode = Integer.parseInt(Code);
        List<OrderMenuDto> list = orderService.selectOrderDetails(orderCode);
        return list;
    }

    public void selectMenuByName(String search){
        MenuDto menu = orderService.selectMenuByName(search);

        if(menu == null){ // 검색결과가 없을 경우
            printResultView.displayFailMessage("search");
        }else{ // 검색결과 있을 경우
            printResultView.displaySearchResult(menu);
        }
    }

}
