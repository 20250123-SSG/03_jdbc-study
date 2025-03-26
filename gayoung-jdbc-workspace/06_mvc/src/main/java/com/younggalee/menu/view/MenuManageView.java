package com.younggalee.menu.view;

import com.younggalee.menu.controller.MenuController;
import com.younggalee.menu.model.dto.MenuDto;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

// 관리자 입장 - 메뉴 관리(목록조회,검색,등록,수정,삭제) 화면
public class MenuManageView {
    private Scanner sc = new Scanner(System.in);
    private MenuController menuController = new MenuController(); // 요청 호출을 위해


    // 메뉴 관리 메인 화면 (관리자 입장으로 진입시 최초 화면)
    public void menuManageMainView() {

        String menu = """
                \n======= 메뉴 관리 화면 =======
                1. 전체 메뉴 조회 
                2. 전체 카테고리 조회 
                3. 신규 메뉴 등록
                4. 메뉴 정보 수정
                5. 메뉴 정보 삭제 
                0. 이전 화면으로 돌아가기 
                >> 입력:""";

        while (true) {
            System.out.println(menu);
            int num = sc.nextInt();
            sc.nextLine();

            switch (num) {
                case 1:
                    menuListView();
                    break;
                case 2:
                    categoryListView();
                    break;
                case 3:
                    registMenuForm();
                    break;
                case 4:
                    modifyMenuForm();
                    break;
                case 5:
                    removeMenuForm();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("메뉴를 다시 입력해주세요.");
            }
        }
    }


    // 전체 메뉴 목록 조회 서브 화면
    public void menuListView(){
        // 요청 == Controller메소드 호출
        List<MenuDto> list = menuController.selectMenuList();

        System.out.println("\n----------------- 조회 결과 -----------------");
        for(MenuDto menu : list){
            System.out.println(menu);
        }
    }

    // 전체 카테고리 목록 조회 서브 화면
    public void categoryListView(){

    }

    // 신규 메뉴 등록용 폼 서브 화면
    public void registMenuForm() {
        System.out.println("\n--------- 신규 메뉴 등록 폼 --------");
        System.out.print("메뉴명 : ");
        String name = sc.nextLine();
        System.out.print("메뉴가격: ");
        String price = sc.nextLine();
        System.out.print("카테고리번호: ");
        String category = sc.nextLine();
        System.out.print("판매내역에 등록(y/n): ");
        String orderable = sc.nextLine(); // 웹에서는 어차피 문자열

        //요청시 전달할 값을 map에 담기 (웹에서 어차피 이렇게함)
        Map<String, String> requestParam = Map.of("name", name,
                "price", price,
                "category", category,
                "orderable", orderable); // 불변객체

        //등록요청
        menuController.registMenu(requestParam);
    }

    //메뉴 정보 수정용 폼 서브 화면
    public void modifyMenuForm(){

    }

    // 메뉴 정보 삭제용 폼 서브 화면
    public void removeMenuForm(){

    }
}
