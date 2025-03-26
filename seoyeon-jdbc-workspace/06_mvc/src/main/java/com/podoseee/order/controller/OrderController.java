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

        int result = orderService.registOrder(order);

        if(result > 0){
            printResultView.displaySuccessMessage("order");
        }else{
            printResultView.displayFailMessage("order");
        }
    }

    public List<OrderDto> selectOrderList(){
        List<OrderDto> list = orderService.selectOrderList();
        return list;
    }

    public List<OrderMenuDto> selectOrderDetail(String code){
        int orderCode = Integer.parseInt(code);
        List<OrderMenuDto> list = orderService.selectOrderDetail(orderCode);
        return list;
    }

    public void selectMenuByName(String search){
        MenuDto menu = orderService.selectMenuByName(search);

        if(menu==null){ // 검색결과 없을 경우
            printResultView.displayFailMessage("search");
        }else{ // 검색결과 있을 경우
            printResultView.displaySearchResult(menu);
        }
    }
}
