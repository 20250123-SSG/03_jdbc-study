package com.minkook.view;

import com.minkook.controller.MenuControleer;
import com.minkook.model.dto.MenuDto;

import java.util.List;
import java.util.Scanner;

//관리자 입장 - 메뉴 관리 (목록 조회,검색, 등록,수정,삭제) 화면
public class MenuManageView {
    private Scanner sc = new Scanner(System.in);
    private MenuControleer menuControleer = new MenuControleer();

    //메뉴 관리 메인 화면(관리자 입장으로 진입시 최초 화면)
    public void menuManageMainView(){
        String menu = """
                \n==================메뉴 관리 화면==================
                1. 전체 메뉴 조회
                2. 전체 카테고리 조회
                3. 신규 메뉴 등록
                4. 메뉴 정보 수정
                5. 메뉴 정보 삭제
                0. 이전 화면으로 돌아가기
                >> 입력:""";

        while (true){
            System.out.print(menu);
            int num = sc.nextInt();
            sc.nextLine();

            switch (num){
                case 1:
                    menuListView();
                    break;
                case 2:
                    categroyListView();
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
                    System.out.println("메뉴를 다시 입력해주세요");
                    break;
            }
        }
    }

    //전체 메뉴 목록 조회 서브 화면
    public void menuListView(){
        List<MenuDto> list = menuControleer.selectMenuList();

        System.out.println("\n===================조회결과=========");
        for(MenuDto menu : list){
            System.out.println(menu);
        }
    }

    public void registMenuForm(){

    }

    public void categroyListView(){

    }

    public void modifyMenuForm(){

    }
    public void removeMenuForm(){

    }
}
