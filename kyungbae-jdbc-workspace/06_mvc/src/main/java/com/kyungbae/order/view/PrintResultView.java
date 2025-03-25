package com.kyungbae.order.view;

public class PrintResultView {
    public void displaySuccessMessage(String code) {
        switch (code) {
            case "order" :
                System.out.println("주문이 성공적으로 완료되었습니다."); break;
        }
    }

    public void displayFailMessage(String code) {
        switch (code) {
            case "order" :
                System.out.println("주문 등록 실패"); break;
        }
    }
}
