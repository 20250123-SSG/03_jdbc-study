package com.seungjoo.run;

import com.seungjoo.menu.view.MenuManageView;

import java.util.Scanner;

public class MainView {
    private Scanner sc = new Scanner(System.in);

    //프로그램 가동시 사용자가 보는 첫 화면
    public void mainMenuView(){

//줄바꿈 자동 가능

        String menu = """ 
                \n========== Kangbroo 가게 ==========
                1. 관리자로 입장
                2. 손님으로 입장 
                0. 프로그램 종료
                >> 입력:""";

        while(true){
            System.out.print(menu);
            int num = sc.nextInt();

            switch(num){
                case 1: new MenuManageView().menuManageMainView(); break;
                case 2:   break;
                case 0: System.out.println("그동안 이용해주셔서 감사합니다."); return;
                default: System.out.println("잘못된 메뉴번호를 눌렀습니다. 다시 선택해주세요.");
            }
        }









    }
}
