package com.podoseee.menu.view;

// 응답 결과를 출력하는 화면
public class PrintResultView {

    // 서비스 요청 성공 메세지 출력용 화면
    public void displaySuccessMessage(String code) {
        switch (code) {
            case "insert":
                System.out.println("메뉴 등록 성공");
                break;
            case "update":
                System.out.println("메뉴 수정 성공");
                break;
            case "delete":
                System.out.println("메뉴 삭제 성공");
                break;
        }
    }

        // 서비스 요청 실패 메세지 출력용 화면
        public void displayFailMessage(String code){
            switch(code){
                case "insert": System.out.println("메뉴 등록 실패"); break;
                case "update": System.out.println("메뉴 수정 실패"); break;
                case "delete": System.out.println("메뉴 삭제 실패"); break;
            }
    }


}
