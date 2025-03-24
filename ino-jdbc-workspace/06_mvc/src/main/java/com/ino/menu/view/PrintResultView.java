package com.ino.menu.view;

public class PrintResultView {

    public static void displaySuccessMessage(String code) {
        switch (code) {
            case "insert":
                System.out.println("메뉴 등록 성공"); break;
            case "update":
                System.out.println("메뉴 수정 성공"); break;
            case "delete":
                System.out.println("메뉴 삭제 성공"); break;
        }
    }

    public static void displayFailureMessage(String code) {
        switch (code) {
            case "insert":
                System.out.println("메뉴 등록 실패"); break;
            case "update":
                System.out.println("메뉴 수정 실패"); break;
            case "delete":
                System.out.println("메뉴 삭제 실패"); break;
        }
    }
}
