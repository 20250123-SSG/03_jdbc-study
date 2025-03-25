package com.kyungbae.run;

import com.kyungbae.model.dto.CategoryDTO;
import com.kyungbae.model.dto.MenuDTO;
import com.kyungbae.model.service.MenuService;

public class Application4 {
    public static void main(String[] args) {
        // 신규 카테고리와 메뉴 동시에 추가하기
        // (둘다 추가가 잘 되어야만 성공)

        // 사용자에게 값 입력받기 (입력받았다고 가정)
        String categoryName = "기타";
        Integer refCategoryCode = null;

        String menuName = "개구리뒷다리살";
        int menuPrice = 7000;
        int categoryCode = 4; // 외래키 제약조건 위배
        String orderableStatus = "Y";

        // 데이터 전송을 위해 DTO 객체에 담기
        CategoryDTO category = new CategoryDTO();
        category.setCategoryName(categoryName);
        category.setRefCategoryCode(refCategoryCode);

        MenuDTO menu = new MenuDTO();
        menu.setMenuName(menuName);
        menu.setMenuPrice(menuPrice);
        menu.setCategoryNo(categoryCode);
        menu.setOrderable(orderableStatus);

        // 해당 비즈니스 로직을 처리할 서비스 호출
        MenuService ms = new MenuService();
        boolean result = ms.registCategoryAndMenu(category, menu);

        // 결과 출력
        if (result) {
            System.out.println("신규 카테고리 및 메뉴등록 성공");
        } else {
            System.out.println("신규 카테고리 및 메뉴등록 실패");
        }

    }
}
