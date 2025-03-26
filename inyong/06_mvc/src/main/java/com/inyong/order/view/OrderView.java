package com.inyong.order.view;

import com.inyong.menu.controller.MenuController;
import com.inyong.menu.model.dto.CategoryDto;
import com.inyong.menu.model.dto.MenuDto;
import com.inyong.order.controller.OrderController;
import com.inyong.order.model.dto.OrderMenuDto;

import java.util.*;

// 손님 입장 - 주문 관련 화면
public class OrderView {

    private Scanner sc = new Scanner(System.in);
    private OrderController orderController = new OrderController();
    private MenuController menuController = new MenuController();


    // 주문 메인 화면
    public void orderMainView() {

        String menu = """
                \n======= 손님 메뉴 =======
                1. 주문하기
                2. 주문내역 확인하기
                0. 이전 화면으로 돌아가기
                >> 입력:""";

        while(true){
            System.out.print(menu);
            int num = sc.nextInt();
            sc.nextLine();

            switch(num){
                case 1: orderForm(); break;
                case 2: orderHistoryView(); break;
                case 0: return;
                default: System.out.println("메뉴를 다시 선택해주세요");
            }
        }

    }

    // 주문 폼 서브 화면
    public void orderForm(){

        System.out.println("\n--------- 주문 폼 ----------");
        // 카테고리 목록 조회 => 주문할 메뉴의 카테고리 번호 입력받기
        List<CategoryDto> categoryList = menuController.selectCategoryList();
        for(CategoryDto category : categoryList){
            System.out.println(category.getCategoryCode() + " : " + category.getCategoryName());
        }
        System.out.print("\n주문하실 메뉴의 카테고리 선택: ");
        String category = sc.nextLine();


        // 해당 카테고리에 주문 가능한 메뉴 목록 조회
        System.out.println("\n------- 주문 가능 메뉴 -------");
        List<MenuDto> orderableMenuList = orderController.selectOrderableMenuList(category);
        for(MenuDto menu : orderableMenuList){
            System.out.println(menu.getMenuCode() + " : " + menu.getMenuName() + ", " + menu.getMenuPrice() + "원");
        }

        // 주문할 메뉴 기록 OrderMenuDto(메뉴번호, 수량) => 반복
        List<OrderMenuDto> orderMenuList = new ArrayList<>();
        int totalPrice = 0;
        while(true){
            System.out.print("주문할 메뉴번호 입력: ");
            int menuCode = sc.nextInt();
            System.out.print("해당 메뉴의 주문수량: ");
            int orderAmount = sc.nextInt();
            sc.nextLine();

            OrderMenuDto orderMenu = new OrderMenuDto();
            orderMenu.setMenuCode(menuCode);
            orderMenu.setOrderAmount(orderAmount);
            orderMenuList.add(orderMenu);

            // 현재 주문하는 메뉴의 가격알아내기
            int menuPrice = 0;
            for(MenuDto menu : orderableMenuList){
                if(menu.getMenuCode() == menuCode){
                    menuPrice = menu.getMenuPrice();
                }
            }

            // 총주문가격 누적
            totalPrice += menuPrice * orderAmount;

            System.out.print("계속 주문하시겠습니까(y/n): ");
            if(sc.nextLine().toUpperCase().charAt(0) == 'N'){
                break;
            }
        }

        System.out.println("\n총 주문금액: " + totalPrice + "원");

        System.out.print("\n주문을 완료하시겠습니까(y/n): ");
        if(sc.nextLine().toUpperCase().charAt(0) == 'N'){
            return;
        }

        // 주문 등록 요청 보내기 (전달할 데이터: 주문메뉴목록, 총주문가격)
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("orderMenuList", orderMenuList);
        requestParam.put("totalPrice", totalPrice);

        orderController.registOrder(requestParam);

    }

    // 주문 내역 조회 서브 화면
    public void orderHistoryView(){

    }
}
