package com.kyungbae.menu.view;

import com.kyungbae.menu.contoller.MenuController;
import com.kyungbae.menu.model.dto.CategoryDto;
import com.kyungbae.menu.model.dto.MenuDto;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

// 관리자 입장 - 메뉴 관리(CRUD) 화면
public class MenuManageView {
    private Scanner sc = new Scanner(System.in);
    private MenuController mc = new MenuController(); // 요청 호출

    // 메누 관리 메인 화면
    public void menuManageMainView() {

        String menu = """
                \n======== 메뉴 관리 화면 ========
                1. 전체 메뉴 조회
                2. 전체 카테고리 조회
                3. 신규 메뉴 등록
                4. 메뉴 정보 수정
                5. 메뉴 정보 삭제
                0. 이전페이지
                >> 입력:""";

        while (true) {
            System.out.print(menu);
            int num = sc.nextInt();
            sc.nextLine();

            switch (num) {
                case 1: menuListView(); break;
                case 2: categoryListView(); break;
                case 3: registMenuForm(); break;
                case 4: modifyMenuForm(); break;
                case 5: removeMenuForm(); break;
                case 0:
                    System.out.println("이전페이지로 이동합니다."); return;
                default:
                    System.out.println("메뉴를 다시 입력해주세요.");
            }
        }

    }

    // 전체 메뉴 목록 조회 서브 화면
    private void menuListView() {
        // 요청 == Controller 메소드 호출
        System.out.println("\n------------- 조회 결과 -------------");
        List<MenuDto> list = mc.selectMenuList();
        if (list.isEmpty()) {
            System.out.println("조회된 메뉴가 없습니다.");
        } else {
            list.forEach(System.out::println);
        }
    }

    // 전체 카테고리 목록 조회 서브 화면
    private void categoryListView() {
        System.out.println("\n------------- 조회 결과 -------------");
        List<CategoryDto> list =  mc.selectCategoryList();
        if (list.isEmpty()) {
            System.out.println("조회된 카테고리가 없습니다.");
        } else {
            list.forEach(System.out::println);
        }

    }

    // 신규 메뉴 등록용
    private void registMenuForm() {
        System.out.println("\n------------- 신규메뉴 등록 폼 -------------");
        System.out.print("메뉴명 : ");
        String name = sc.nextLine();
        System.out.print("메뉴 가격 : ");
        String price = sc.nextLine();
        System.out.print("카테고리 번호 : ");
        String categoryCode = sc.nextLine();
        System.out.print("판매 가능 여부 (y/n) : ");
        String orderable = sc.nextLine();

        // 요청시 전달할 값 Map에 담기
        Map<String ,String> requestParam = Map.of(
                "name", name,
                "price", price,
                "categoryCode", categoryCode,
                "orderable", orderable
        );

        // 등록 요청
        mc.registMenu(requestParam);
    }

    // 메뉴 수정용
    private void modifyMenuForm() {
        System.out.println("\n------------- 메뉴 수정 폼 -------------");
        System.out.print("수정할 메뉴 번호 입력 : ");
        String menuCode = sc.nextLine();

        // 해당 메뉴 조회
        System.out.println("------------- 수정할 메뉴 정보 -------------");
        int result = mc.menuView(menuCode);
        if (result == 0) {
            return;
        }

        System.out.print("해당 메뉴를 수정하시겠습니까? (y/n) : ");
        String agreement = sc.nextLine().toLowerCase();
        if (agreement.equals("y")) {
            System.out.println("------- 수정 메뉴 정보 입력 (수정안할 시 0 입력) -------");
            System.out.print("수정할 메뉴 이름 : ");
            String menuName = sc.nextLine();
            System.out.print("수정할 가격 : ");
            String menuPrice = sc.nextLine();
            System.out.print("수정할 카테고리 번호 : ");
            String categoryCode = sc.nextLine();
            System.out.print("판매 여부 변경 (y/n) : ");
            String orderable = sc.nextLine();

            Map<String, String> requestParam = Map.of(
                    "menuCode", menuCode,
                    "menuName", menuName,
                    "menuPrice", menuPrice,
                    "categoryCode", categoryCode,
                    "orderable", orderable
            );

            MenuDto originMenu = mc.selectMenu(menuCode);

            mc.modifyMenu(requestParam, originMenu);
        } else {
            System.out.println("이전 화면으로 돌아갑니다.");
        }
    }

    // 메뉴 삭제용
    private void removeMenuForm() {
        System.out.println("\n------------- 메뉴 삭제 폼 -------------");
        System.out.print("삭제할 메뉴 번호 입력 : ");
        String menuCode = sc.nextLine();

        System.out.println("------------- 삭제할 메뉴 정보 -------------");
        int result = mc.menuView(menuCode);
        if (result == 0) {
            return;
        }

        System.out.print("해당 메뉴를 수정하시겠습니까? (y/n) : ");
        String agreement = sc.nextLine().toLowerCase();
        if (agreement.equals("y")) {
            mc.removeMenu(menuCode);
        } else {
            System.out.println("이전 화면으로 돌아갑니다.");
        }
    }

    /*
    // 메뉴코드로 메뉴 조회
    private void menuView() {
        String menuCode = sc.nextLine();
        MenuDto originMenu = mc.selectMenu(menuCode);
        if (originMenu.getMenuCode() != 0) {
            System.out.println(originMenu);
        } else {
            System.out.println("조회된 메뉴가 없습니다.");
        }
    }
     */

}
