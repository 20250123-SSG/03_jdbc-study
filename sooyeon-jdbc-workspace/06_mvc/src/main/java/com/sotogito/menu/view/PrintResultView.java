package com.sotogito.menu.view;

import com.sotogito.menu.MenuFunction;

/// 응답 결과를 출력하는 화면 템플릿같은것.
public class PrintResultView {

    public void displaySuccessMessage(MenuFunction function) {
        switch (function) {
            case INSERT : System.out.println("메뉴 등록 성공"); break;
            case UPDATE : System.out.println("메뉴 수정 성공"); break;
            case DELETE : System.out.println("메뉴 삭제 성공"); break;
        }
    }

    public void displayFailMessage(MenuFunction function) {
        switch (function) {
            case INSERT : System.out.println("메뉴 등록 실패"); break;
            case UPDATE : System.out.println("메뉴 수정 실패"); break;
            case DELETE : System.out.println("메뉴 삭제 실패"); break;
        }
    }

}
