package com.ino.run;

import com.ino.menu.view.MenuManageView;

import java.util.Scanner;

public class MainView {
    MenuManageView menuManageView = new MenuManageView();
    public void mainMenuView(){
        Scanner sc = new Scanner(System.in);
        String menu = """
                1. 관리자로 입장
                2. 손님으로 입장
                0. 프로그램 종료
                >> 입력:""";

        while (true) {
            System.out.print(menu);
            int num = sc.nextInt();

            switch (num){
                case 1: menuManageView.menuManageMainView();
                    break;
                case 2: break;
                case 0:
                    System.out.println("thanks for using"); return;
                default:
                    System.out.println("wrong menu selected");
            }
        }
    }
}
