package com.sotogito.order.view;

import com.sotogito.menu.controller.MenuController;
import com.sotogito.menu.model.dto.MenuDto;
import com.sotogito.order.controller.OrderController;
import com.sotogito.order.model.dto.OrderMenuDto;

import java.util.*;

public class OrderView {
    private final Scanner sc = new Scanner(System.in);
    private final OrderController orderController = new OrderController();
    private final MenuController menuController = new MenuController();

    public void orderMainView() {
        String menu = """
                
                
                ===== 손님 메뉴 =====
                1. 주문하기
                2. 주문내역 확인하기
                3. 메뉴 검색
                0. 이전 화면으로 돌아가기
                >>> 입력:
                """;

        while (true) {
            System.out.println(menu);
            int menuFunc = Integer.parseInt(sc.nextLine());

            switch (menuFunc) {
                case 1: orderForm();        break;
                case 2: orderHistoryView(); break;
                case 3: searchMenuView();   break;
                case 0:
                    System.out.println("이전 페이지로 이동합니다.");
                    return;
                default:
                    System.out.println("메뉴를 다시 선택해주세요.");
            }
        }
    }

    private void orderForm() {
        /**
         * 1. 카테고리 목록 조회
         * 2. 카테고리 번호 입력받기
         * 3. 해당 카테고리에 주문 가능한 메뉴 목록 조회
         * 4. 주문할 메뉴 기록 ( 메뉴번호, 수량 )
         */
        System.out.println("===== 주문 폼 =====");
        menuController.selectCategoryList().forEach(System.out::println); /// 재사용

        System.out.println("카테고리 코드를 입력해주세요.");
        String categoryCode = sc.nextLine();

        List<MenuDto> orderableMenuList = orderController.selectOrderableMenuList(categoryCode);
        orderableMenuList.forEach(System.out::println);
        /**
         * 1. List<OrderDto> 형식으로 컨트롤러 넘기기
         * 2. 컨트롤러에서 서비스로 넘기기
         * 3. 서비스에서 tbl_order 동록 후 코드 반환받기
         * 4. 코드로 tbl_order_menu 추가하기 -> 아닌가 조인으로 한번엫ㄹ 수 있나

         */
        List<OrderMenuDto> orderMenuList = new ArrayList<>();
        int totalOrderPrice = 0;
        while (true) {
            System.out.println("주문할 메뉴 코드와 수량을 입력해주세요.");
            int menuCode = Integer.parseInt(sc.nextLine());
            int menuAmount = Integer.parseInt(sc.nextLine());

            OrderMenuDto orderMenuDto = new OrderMenuDto();
            orderMenuDto.setMenuCode(menuCode);
            orderMenuDto.setOrderAmount(menuAmount);

            orderMenuList.add(orderMenuDto);

            int menuPrice = 0;
            try {
                menuPrice = orderableMenuList.stream()
                        .filter(menu -> menu.getMenuCode() == menuCode)
                        .mapToInt(menu -> menu.getMenuPrice())
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다."));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
            totalOrderPrice += (menuPrice * menuAmount);

            System.out.println("계속 주문하시겠습니까?");
            if (sc.nextLine().equalsIgnoreCase("N")) {
                break;
            }
        }

        System.out.println(totalOrderPrice);

        System.out.println("주무ㅏㅇㄹ할거?");
        if (sc.nextLine().equalsIgnoreCase("N")) {
            return;
        }

        Map<String, Object> requestParams = Map.of(
                "orderMenuList", orderMenuList,
                "totalOrderPrice", totalOrderPrice
        );

        orderController.registOrder(requestParams);
    }

    /**
     * 1. 주문 번호 입력받기
     * 2. 해당 주문에 어떤 메뉴들이 주문되었는지 조회 - 매뉴 번호, 메뉴명, 메뉴 가격, 카테고리명
     */
    private void orderHistoryView() {
        System.out.println("조회할 주문 번호를 입력해주세요.");
        String orderCode = sc.nextLine();

        List<MenuDto> orderMenuDetailList = null;
        try {
            orderMenuDetailList = orderController.selectOrderMenuDetailList(orderCode);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        orderMenuDetailList.forEach(System.out::println);
    }

    /**
     * // 3. 메뉴 검색
     * //    검색할 메뉴명 입력받아서 해당 메뉴명과 일치하는 메뉴정보 조회
     */
    private void searchMenuView() {
        System.out.println("조회하고 싶은 메뉴명을 입력해주세요.");
        String menuName = sc.nextLine();

        MenuDto menu = null;
        try {
            menu = orderController.selectMenuByMenuName(menuName);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(menu);
    }

}
