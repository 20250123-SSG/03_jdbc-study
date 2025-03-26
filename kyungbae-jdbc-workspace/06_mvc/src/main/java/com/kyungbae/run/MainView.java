package com.kyungbae.run;

import com.kyungbae.menu.view.MenuManageView;
import com.kyungbae.order.view.OrderView;

import java.util.Scanner;

public class MainView {
    Scanner sc = new Scanner(System.in);

    // 프로그램 기동시 사용자가 처음 보게될 메인화면
    public void mainMenuView(){
        String menu = """
                
                ============ Main Menu =============
                1. 관리자로 입장
                2. 손님으로 입장
                0. 프로그램 종료
                >> 입력 : """;
        while (true) {
            System.out.print(menu);
            int selectMainMenu = sc.nextInt();

            switch (selectMainMenu) {
                case 1: new MenuManageView().menuManageMainView(); break;
                case 2: new OrderView().orderMainView(); break;
                case 0:
                    System.out.println("종료합니다."); return;
                default:
                    System.out.println("다시 선택해주세요.");
            }
        }
    }

}
