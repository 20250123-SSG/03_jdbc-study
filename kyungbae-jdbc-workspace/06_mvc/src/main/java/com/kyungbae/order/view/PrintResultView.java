package com.kyungbae.order.view;

import com.kyungbae.menu.model.dto.MenuDto;

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
            case "search" :
                System.out.println("검색 결과가 없습니다."); break;
        }
    }

    public void displaySearchResult(MenuDto menu) {
        System.out.println("\n검색결과 : ");
        System.out.println(menu);
    }
}
