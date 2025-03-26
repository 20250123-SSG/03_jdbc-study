package com.younggalee.menu.view;

public class PrintResultView {
    public void displaySuccessMessage(String code){
        switch(code){
            case "insert" :
                System.out.println("등록 성공"); break;
            case "update":
                System.out.print("수정성공"); break;
            case "delete":
                System.out.print("삭제성공"); break;
        }
    }

    // 서비스 요청 실패 메세지 출력용 화면
    public void displayFailMessage(String code){
        switch(code){
            case "insert" :
                System.out.println("등록  실패"); break;
            case "update":
                System.out.print("수정 실패"); break;
            case "delete":
                System.out.print("삭제 실패"); break;
        }
    }

}
