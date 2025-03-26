package com.minkook.run;
/*
    ## View
    1. 사용자에게 시각적으로 보여지는 요소를 담당
    2. 입력폼과 출력결과를 제공함
    3. 처리과정
        1) 요청에 따라 입력값이 필요할 경우 사용자에게 값 입력받기
        2) 요청을 처리해주는 Controller 측 메소드 호출[값 전달]
            요청시 전달되는 값 == request parameter
 */

import java.util.Scanner;

public class MainView {

    private Scanner sc = new Scanner(System.in);

    //프로그램 기둥시 사용자가 처음 보게될 메인 화면
    public void mainMenuView(){
        String menu = """
                ========minkook가게=========
                1. 관리자로 입장
                2. 손님으로 입장
                0. 프로그램 종료
                >> 입력: """;

        while(true){
            System.out.print(menu);
            int num = sc.nextInt();

            switch (num){
                case 1:
                    break;
                case 2:
                    break;
                case 0:
                    System.out.println("그 동안 이용해주셔서 감사합니다");
                    return;
                default:
                    System.out.println("잘못된 메뉴번호를 눌렀습니다. 다시 선택해주세요");

            }
        }
    }
}
