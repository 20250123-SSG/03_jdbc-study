package com.podoseee.run;

import com.podoseee.model.dto.CategoryDTO;
import com.podoseee.model.dto.MenuDTO;
import com.podoseee.model.service.MenuService;

public class Application4 {

    public static void main(String[] args) {

        // 신규 카테고리와 메뉴 동시에 추가

        // 사용자에게 값 입력 (입력받았다고 가정)
        String categoryName = "기타";
        Integer refCategoryCode = null;

        String menuName = "개구리뒷다리살";
        int menuPrice = 7000;
        int categoryCode = 4;
        String orderableStatus = "Y";


        // 데이터 전송을 위해 DTO 객체 담기
        CategoryDTO category = new CategoryDTO();
        category.setCategoryName(categoryName);
        category.setRefCategoryCode(refCategoryCode);

        MenuDTO menu = new MenuDTO();
        menu.setMenuName(menuName);
        menu.setMenuPrice(menuPrice);
        menu.setCategoryCode(categoryCode);
        menu.setOrderableStatus(orderableStatus);

        // 해당 비즈니스 로직을 처리할 서비스 호출
        int result = new MenuService().registCategoryAndMenu(category, menu);

        // 결과 출력
        if(result > 0){
            System.out.println("등록 성공");
        }else{
            System.out.println("등록 실패");
        }



    }


}