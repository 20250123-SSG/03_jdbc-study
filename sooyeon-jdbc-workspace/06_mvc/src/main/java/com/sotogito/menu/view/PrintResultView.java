package com.sotogito.menu.view;

/// 응답 결과를 출력하는 화면 템플릿같은것.
public class PrintResultView {

    public void displaySuccessMessage(String code) {
        switch (code) {
            case "insert": System.out.println("메뉴 등록 성공"); break;
            case "update": System.out.println("메뉴 수정 성공"); break;
            case "delete": System.out.println("메뉴 삭제 성공"); break;
        }
    }

    public void displayFailMessage(String code) {
        switch (code) {
            case "insert": System.out.println("메뉴 등록 실패"); break;
            case "update": System.out.println("메뉴 수정 실패"); break;
            case "delete": System.out.println("메뉴 삭제 실패"); break;
        }
    }

}
