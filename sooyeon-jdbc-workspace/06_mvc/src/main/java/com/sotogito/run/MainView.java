package com.sotogito.run;

import com.sotogito.menu.view.MenuManageView;
import com.sotogito.order.view.OrderView;

import java.util.Scanner;

/**
 * ## View
 * 1. 사용자에게 시각적으로 보여지는 요소를 담당
 * 2. 입력폼과 출력결과를 제공함
 * 3. 처리 과정
 *      - 요청에 따라 입력값이 필요할 경우 사용자에게 값 입력받기
 *      - 요청을 처리해주는 Controller측 메서드 호출(값 전달)
 *         요청시 전달되는 값 == request parameter
 */
public class MainView {
    private Scanner sc = new Scanner(System.in);
    private static  String menu = """
                
                
                =====가게=====
                
                1. 관리자로 입장
                2. 손님으로 입장
                0 프로그램 종료
                입력 :
                """;

    ///  프로그램 기동시 사용자가 처음 보게될 메인 화면
    public void mainMenuView() {

        while (true) {
            System.out.println(menu);
            int funcNum = Integer.parseInt(sc.nextLine());

            switch (funcNum) {
                case 1: new MenuManageView().menuManageMainView();   break;
                case 2: new OrderView().orderMainView();             break;
                case 0:
                    System.out.println("ㅃㅃ"); return;
                default:
                    System.out.println("존재하지 않는 메뉴입니다.");
            }
        }
    }

}
