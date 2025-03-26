package com.younggalee.run;

public class Application {
    public static void main(String[] args) {

        /*
            ## MVC 패턴 ##
            1. 자바 백엔드에서 가장 대중적으로 사용되는 패턴
            2. 데이터(Model), UI담당(View), 요청처리(Controller)을 역할별로 명확히 분리해서 작성
            3. 장점
               - 개발 속도 향상
               - 재사용성 증가
               - 유지보수성 증가
               - 확장성
         */

        new MainView().mainMenuView();

    }
}