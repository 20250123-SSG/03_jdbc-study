package com.sotogito.menu.view;

import com.sotogito.menu.controller.MenuController;

import java.util.Map;
import java.util.Scanner;

///  관리자 입장 - 메뉴 관리(목록조회, 검색, 등록, 수정, 삭제) 화면
public class MenuManageView {
    private static String menu = """
            
            
            ===== 메뉴 관리 화면=====
            
            1. 전체 메뉴 조회
            2. 전체 카테고리 조회
            3. 신규 메뉴 등록
            4. 메뉴 정보 수정
            5. 메뉴 정보 삭제
            0. 뒤로가기
            입력 :
            """;
    private Scanner sc = new Scanner(System.in);
    private MenuController menuController = new MenuController();


    ///  메뉴 관리 메인 화면 관리자 입장으로 진입시 최초 화면
    public void menuManageMainView() {
        while (true) {
            System.out.println(menu);
            int funcNum = Integer.parseInt(sc.nextLine());

            switch (funcNum) {
                case 1: menuListView(); break;
                case 2: cateogryListView(); break;
                case 3: registMenuForm(); break;
                case 4: modifyMenuForm(); break;
                case 5: removeMenuForm(); break;
                case 0:
                    System.out.println("이전 화면으로 돌아갑니다."); return;
                default:
                    System.out.println("존재하지 않는 메뉴입니다.");break;
            }
        }
    }


    private void menuListView() {
        System.out.println("전체 메뉴를 출력합니다.");
        try {
            menuController.selectMenuList().forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void cateogryListView() {
        System.out.println("전체 카테고리 출력합니다.");
        try {
            menuController.selectCategoryList().forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void registMenuForm() {
        System.out.println("=====신규메뉴등록=====");
        System.out.println("메뉴명, 가격, 카테고리번호, 판매여부(y/n)를 입력해주세요.");
        String menuName = sc.nextLine();
        String menuPrice = sc.nextLine();
        String categoryNum = sc.nextLine();
        String orderable = sc.nextLine();

        /// 요청시 전달할 값을 dto가 아니라~~~~~~~~ Map에 담아보기
        Map<String,String> requestParam = Map.of(
                "menuName",menuName
                ,"menuPrice",menuPrice
                ,"categoryNum",categoryNum
                ,"orderable",orderable
        );

        /// 등록요청
        menuController.registMenu(requestParam);
    }

    private void modifyMenuForm() {
        menuListView();
        System.out.println("=====메뉴정보수정=====");
        System.out.println("수정할 메뉴의 메뉴 번호를 입력하세요");
        String updateMenuCode = sc.nextLine();
        System.out.println("수정할 메뉴의 이름, 가격, 카테고리코드, 판매여부를 입력하세요");
        String menuName = sc.nextLine();
        String menuPrice = sc.nextLine();
        String categoryNum = sc.nextLine();
        String orderable = sc.nextLine();

        Map<String,String> requestParam = Map.of(
                "updateMenuCode",updateMenuCode
                ,"menuName",menuName
                ,"menuPrice",menuPrice
                ,"categoryNum",categoryNum
                ,"orderable",orderable
        );

        menuController.updateMenu(requestParam);
    }

    private void removeMenuForm() {
        menuListView();
        System.out.println("===== 메뉴 삭제=====");
        System.out.println("삭제할 메뉴 코드를 입력하세요.");
        String removeMenuCode = sc.nextLine();

        Map<String, String> requestParam = Map.of(
                "removeMenuCode",removeMenuCode
        );
        menuController.deleteMenu(requestParam);
    }

}
