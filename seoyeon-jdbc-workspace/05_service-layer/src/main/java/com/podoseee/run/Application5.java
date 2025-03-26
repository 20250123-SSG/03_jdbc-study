package com.podoseee.run;

import com.podoseee.model.dto.CategoryDTO;
import com.podoseee.model.dto.MenuDTO;
import com.podoseee.model.service.MenuService;

import java.util.Scanner;

public class Application5 {
    public static void main(String[] args) {
        // 신규카테고리 등록 후 등록시 생성된 카테고리번호로 신규 메뉴 추가

        // 사용자에게 등록할 데이터 입력받기
        Scanner sc = new Scanner(System.in);
        // > 카테고리 정보 입력
        System.out.print("등록시킬 카테고리명 : ");
        String categoryName = sc.nextLine();
        System.out.print("등록시킬 카테고리의 상위카테고리번호 : ");
        Integer refCategoryCode = sc.nextInt();
        sc.nextLine();

        // > 메뉴 정보 입력
        System.out.print("\n등록시킬 메뉴명: ");
        String menuName = sc.nextLine();
        System.out.print("등록시킬 메뉴가격: ");
        int menuPrice = sc.nextInt();
        sc.nextLine();
        System.out.print("주문가능여부(y/n): ");
        String orderableStatus = sc.nextLine().toUpperCase();

        // 요청시 전달할 값 dto에 담기
        CategoryDTO category = new CategoryDTO();
        category.setCategoryName(categoryName);
        category.setRefCategoryCode(refCategoryCode);

        MenuDTO menu = new MenuDTO();
        menu.setMenuName(menuName);
        menu.setMenuPrice(menuPrice);
        menu.setOrderableStatus(orderableStatus);

        // 해당 비즈니스 로직을 처리할 서비스 호출
        MenuService menuService = new MenuService();
        int result = menuService.registCategoryAndMenu2(category, menu);

        // 반환받은 결과를 통해 응답 결정
        if(result > 0){
            System.out.println("등록 성공");
        } else {
            System.out.println("등록 실패");
        }

    }
}












