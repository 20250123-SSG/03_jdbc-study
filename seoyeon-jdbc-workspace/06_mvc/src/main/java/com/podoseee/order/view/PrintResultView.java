package com.podoseee.order.view;

import com.podoseee.menu.model.dto.CategoryDto;
import com.podoseee.menu.model.dto.MenuDto;
import com.podoseee.order.model.dto.OrderMenuDto;

import java.util.ArrayList;
import java.util.List;

public class PrintResultView {

    public void displaySuccessMessage(String code) {
        switch (code) {
            case "order":
                System.out.println("주문 등록 성공");
        }
    }

    public void displayFailMessage(String code) {
        switch (code) {
            case "order": System.out.println("주문 등록 실패"); break;
            case "search": System.out.println("검색 결과가 없습니다."); break;
        }
    }

    public void displaySearchResult(MenuDto menu){
        System.out.println("\n검색된 결과가 다음과 같습니다.");
        System.out.println(menu);
    }
}
