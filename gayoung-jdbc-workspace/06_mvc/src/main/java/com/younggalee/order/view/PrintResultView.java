package com.younggalee.order.view;

public class PrintResultView {
    public void displaySuccessMessage(String code){
        switch(code){
            case "order" :
                System.out.println("주문 등록 성공");
        }
    }


    public void displayFailMessage(String code){
        switch(code){
            case "order" :
                System.out.println("주문 등록 실패");
        }
    }

}
