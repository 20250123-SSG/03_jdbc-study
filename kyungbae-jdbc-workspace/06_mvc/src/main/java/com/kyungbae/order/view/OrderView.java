package com.kyungbae.order.view;

import com.kyungbae.menu.contoller.MenuController;
import com.kyungbae.menu.model.dto.CategoryDto;
import com.kyungbae.menu.model.dto.MenuDto;
import com.kyungbae.order.controller.OrderController;
import com.kyungbae.order.model.dto.OrderDto;
import com.kyungbae.order.model.dto.OrderMenuDto;

import java.util.*;

// 손님용 메인 메뉴
public class OrderView {
    private Scanner sc = new Scanner(System.in);
    private OrderController orderController = new OrderController();
    private MenuController menuController = new MenuController();


    // 주문 메인 화면
    public void orderMainView() {
        String menu = """
                \n========= 손님 메뉴 =========
                1. 주문하기
                2. 주문내역 확인
                0. 이전화면으로 돌아가기.
                >> 입력 :""";

        while (true) {
            System.out.print(menu);
            int num = sc.nextInt();
            sc.nextLine();

            switch (num) {
                case 1: orderForm(); break;
                case 2: orderHistoryView(); break;
                case 0: return;
                default:
                    System.out.println("메뉴를 다시 선택해주세요");
            }
        }
    }

    // 주문 페이지
    private void orderForm() {
        // 카테고리 목록 조회
        System.out.println("\n---------------- 주문 ----------------");
        List<CategoryDto> categoryList = menuController.selectCategoryList();
        for (CategoryDto category : categoryList) {
            System.out.println(category.getCategoryCode() + " : " + category.getCategoryName());
        }
        System.out.print("\n주문하실 메뉴의 카테고리 선택: ");
        String category = sc.nextLine();

        // 해당 카테고리에 주문 가능한 메뉴 목록 조회
        System.out.println("\n------------- 주문 가능 메뉴 -------------");
        List<MenuDto> orderableMenuList = orderController.selectOrderableMenuList(category);
        for (MenuDto menu : orderableMenuList) {
            System.out.println(menu.getMenuCode() + " : " + menu.getMenuName() + ", " + menu.getMenuPrice() + "원");
        }

        // 주문할 메뉴 기록 (메뉴번호, 주문수량) => 반복
        List<OrderMenuDto> orderMenuList = new ArrayList<>();
        int totalprice = 0; // 총 주문 금액

        while (true) {
            System.out.print("\n주문할 메뉴 번호 입력: ");
            int menuCode = sc.nextInt();
            System.out.print("해당 메뉴의 주문 수량: ");
            int orderAmount = sc.nextInt();
            sc.nextLine();

            OrderMenuDto orderMenu = new OrderMenuDto();
            orderMenu.setMenuCode(menuCode);
            orderMenu.setOrderAmount(orderAmount);

            orderMenuList.add(orderMenu);

            // 현재 주문하는 메뉴의 가격
            int menuPrice = 0;
            for (MenuDto menu : orderableMenuList) {
                if (menu.getMenuCode() == menuCode) {
                    menuPrice = menu.getMenuPrice();
                }
            }

            // 총 주문 가격 (누적)
            totalprice += menuPrice * orderAmount;
            System.out.println("주문이 추가되었습니다.");

            System.out.print("계속 주문을 하시겠습니까? (y/n): ");
            if (sc.nextLine().toUpperCase().equals("N")) {
                break;
            }
        }
        System.out.println("총 주문 금액 : " + totalprice + "원");

        System.out.print("\n주문을 완료하시겠습니까(y/n): ");
        if(sc.nextLine().toUpperCase().charAt(0) == 'N'){
            return;
        }

        // 주문 등록 요청
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("orderMenuList", orderMenuList);
        requestParam.put("totalPrice", totalprice);

        orderController.registOrder(requestParam);

    }

    // 주문 내역 조회
    private void orderHistoryView() {
        // 전체 주문 목록 조회해서 출력
        // 주문번호, 주문날짜, 주문시간, 주문가격
        System.out.println("\n-------- 전체 주문 내역 --------");
        List<OrderDto> list = orderController.selectOrder();
        for (OrderDto order : list) {
            System.out.println(order.getOrderCode() +
                    " : " + order.getOrderDate() +
                    " " + order.getOrderTime() +
                    ", " + order.getTotalOrderPrice() + "원");
        }

        // 사용자에게 상세조회할 주문번호 입력받기
        System.out.print("\n상세조회할 주문번호 입력: ");
        int orderCode = sc.nextInt();

        // 사용자에게 상세조회 내용 출력
        // 메뉴번호, 메뉴명, 메뉴가격, 카테고리명
        System.out.println("\n---------- 상세 내역 -----------");
        List<MenuDto> menuList = orderController.retrieveOrderMenu(orderCode);
        for (MenuDto menu : menuList) {
            System.out.println(menu.getMenuCode() +
                    " : " + menu.getMenuName() +
                    ", " + menu.getMenuPrice() + "원" +
                    ", " + menu.getCategory());
        }

    }
}
