package com.younggalee.order.view;


import com.younggalee.menu.controller.MenuController;
import com.younggalee.menu.model.dto.CategoryDto;
import com.younggalee.menu.model.dto.MenuDto;
import com.younggalee.order.controller.OrderController;
import com.younggalee.order.model.dto.OrderMenuDto;

import java.util.*;

// 손님 입장 - 주문 관련 화면
public class OrderView {
    private Scanner sc = new Scanner(System.in);
    private OrderController orderController = new OrderController();
    // com/younggalee/menu/controller/MenuController.java 재사용
    private MenuController menuController = new MenuController();
    // 주문 메인 화면
    public void orderMainView() {
        String menu = """
                \n========= 손님 메뉴 ==========
                1. 주문하기
                2. 주문내역 확인하기
                0. 이전 화면으로 돌아가기
                >> 입력 : """;
        while(true){
            System.out.print(menu);
            int num = sc.nextInt();
            sc.nextLine();

            switch(num){
                case 1: orderForm(); break;
                case 2: orderHistoryView(); break;
                case 0: return;
                default:
                    System.out.println("메뉴를 다시 선택해주세요.");
            }
        }
    }

    // 주문 폼 서브 화면
    public void orderForm(){
        System.out.println("\n ----------- 주문 폼 ------------");
        //카테고리 목록 조회 >> 주문할 메뉴의 카테고리 번호 입력받기
        List<CategoryDto> categoryDtoList = menuController.selectCategoryList();
        for (CategoryDto category : categoryDtoList) {
            System.out.println(category.getCategoryCode() + " : " + category.getCategoryName());
        }
        System.out.print("\n 주문하실 메뉴의 카테고리 선택 : ");
        String categoryNum = sc.nextLine();

        // 해당 카테고리에 주문 가능한 메뉴 목록 조회
        List<MenuDto> orderableMenuList = orderController.selectOrderableMenuList(categoryNum);
        // 출력
        for (MenuDto menu : orderableMenuList) {
            // 가져온 것만 있는게 아니라 없는 데이터는 null로 가지고 있기 때문에 따로 출력문 작성함.
            System.out.println(menu.getMenuCode() + " : " + menu.getMenuName() + ", " + menu.getMenuPrice() + "원");
        }

        // 주문할 메뉴 기록 (메뉴번호, 수량) >> 반복
        // OrderMenuDto에 리스트로 담아서 진행할 예정
        List<OrderMenuDto> orderMenuList = new ArrayList<>();
        int totalPrice = 0;

        while(true){
            System.out.print("주문할 메뉴 코드: ");
            int menuCode = sc.nextInt();
            sc.nextLine();
            System.out.print("주문할 수량 : ");
            int orderAmount = sc.nextInt();
            sc.nextLine();


            OrderMenuDto orderMenu = new OrderMenuDto();
            orderMenu.setMenuCode(menuCode);
            orderMenu.setOrderAmount(orderAmount);
            orderMenuList.add(orderMenu);

            //현재 주문하는 메뉴의 가격 알아내기
            int menuPrice = 0;
            for (MenuDto menu : orderableMenuList){
                if (menu.getMenuCode() == menuCode){
                    menuPrice = menu.getMenuPrice();
                }
            }

            // 총 주문가격 누적
            totalPrice += menuPrice * orderAmount;

            System.out.print("계속 주문하시겠습니까? (y/n) : ");
            if (sc.nextLine().toUpperCase().charAt(0) == 'N'){
                break;
            }
        }
        System.out.println("총주문금액 : " + totalPrice + "원");

        // 주문 그만하겠습니까? > Y : return

        // 주문등록 요청 보내기 (전달 데이터 : 주문메뉴목록, 총주문금액)
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("orderMenuList", orderMenuList);
        requestParam.put("totalPrice", totalPrice);

        orderController.registOrder(requestParam);

    }

    // 주문 내역 조회 서브 화면
    public void orderHistoryView() {
        //전체 주문 목록 조회해서 출력
        // 주문번호, 주문날짜, 주문시간, 주문가격

        //사용자에게 상세조회할 주문번호 입력받기

        // 해당 주문에 어떤 메뉴들이 주문되었는지 조회해보기
        // 메뉴번호, 메뉴명, 메뉴가격, 카테고리명

    }
}

















