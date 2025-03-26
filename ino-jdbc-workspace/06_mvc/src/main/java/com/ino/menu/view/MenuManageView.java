package com.ino.menu.view;

import com.ino.menu.controller.MenuController;
import com.ino.menu.model.dao.MenuDao;
import com.ino.menu.model.dto.CategoryDto;
import com.ino.menu.model.dto.MenuDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuManageView {

    private Scanner sc = new Scanner(System.in);
    private MenuController menuController = new MenuController();

    public void menuManageMainView() {
        String menu = """
                \n======= 메뉴 관리 페이지 =======
                1. 전체 메뉴 조회
                2. 전체 카테고리 조회
                3. 신규 메뉴 등록
                4. 메뉴 정보 수정
                5. 메뉴 정보 삭제
                0. 이전 페이지로 돌아가기
                >>> 입력:""";
        while(true) {
            System.out.print(menu);
            int num = sc.nextInt();
            sc.nextLine();
            switch (num){
                case 1: menuListView(); break;
                case 2: getAllCategory(); break;
                case 3: registMenuForm(); break;
                case 4: updateMenuForm(); break;
                case 0:
                    System.out.println("thanks for using"); return;
                default:
                    System.out.println("wrong menu selected");
            }
        }
    }

    public void menuListView() {
        List<MenuDto> list = menuController.selectMenuList();

        System.out.println("\n============= 조회 결과 =============");
        for(MenuDto m : list) {
            System.out.println(m.toString());
        }
    }

    public void getAllCategory() {
        List<CategoryDto> list = menuController.getAllCategory();

        System.out.println("\n============= 조회 결과 =============");
        for(CategoryDto c : list) {
            System.out.println(c.toString());
        }
    }

    public void registMenuForm() {
        System.out.println("\n------- 신규 메뉴 등록 폼 -------");
        System.out.print("메뉴명: ");
        String menuName = sc.nextLine();
        System.out.print("메뉴 가격: ");
        String menuPrice = sc.nextLine();
        System.out.println("카테고리 코드: ");
        String categoryCode = sc.nextLine();
        System.out.println("주문가능여부(y,n): ");
        String orderableStatus = sc.nextLine();
        Map<String, String> requestParam = Map.of("name", menuName,
                "price", menuPrice
                ,"category", categoryCode
                , "orderable", orderableStatus);
        menuController.insertMenu(requestParam);
    }

    public void updateMenuForm() {
        System.out.println("변경 메뉴 코드: ");
        int num = sc.nextInt();
        sc.nextLine();
        System.out.println("\n------- 메뉴 수정 폼 -------");
        System.out.print("메뉴명: ");
        String menuName = sc.nextLine();
        System.out.print("메뉴 가격: ");
        String menuPrice = sc.nextLine();
        System.out.println("카테고리 코드: ");
        String categoryCode = sc.nextLine();
        System.out.println("주문가능여부(y,n): ");
        String orderableStatus = sc.nextLine();
        Map<String, String> requestParam = Map.of("name", menuName,
                "price", menuPrice
                ,"category", categoryCode
                , "orderable", orderableStatus);
        menuController.updateMenu(num, requestParam);
    }

    public void modifyMenuForm() {

    }

    public void categoryListView(){

    }
}
