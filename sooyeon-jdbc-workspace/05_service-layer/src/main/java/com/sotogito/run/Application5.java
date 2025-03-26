package com.sotogito.run;

import com.sotogito.model.dto.CategoryDTO;
import com.sotogito.model.dto.MenuDTO;
import com.sotogito.model.service.MenuService;

import java.util.Scanner;

public class Application5 {

    public static void main(String[] args) {
        // 신규카테고리 등록 후 등록시 생성된 카테고리번호로 신규 메뉴 추가

        ///  사용자에게 등록할 데이터 입력받기
        Scanner sc = new Scanner(System.in);
        System.out.println("등록시킬 카테고리명을 입력하세요");
        String categoryName = sc.nextLine();
        System.out.println("등록시킬 카테고리의 상위 카테고리 번호");
        Integer refCategoryCode = Integer.parseInt(sc.nextLine());

        System.out.println("등록시킬 메뉴명, 가격, 주문가능여부");
        String menuName = sc.nextLine();
        int menuPrice = Integer.parseInt(sc.nextLine());
        String orderableStatus = sc.nextLine().toUpperCase();


        /// DTO에 데이터 담기
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName(categoryName);
        categoryDTO.setRefCategoryCode(refCategoryCode); //참조 카테고리지 PK가 아님

        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setMenuName(menuName);
        menuDTO.setMenuPrice(menuPrice);
        menuDTO.setOrderableStatus(orderableStatus);

        MenuService menuService = new MenuService();
        int insertResult = menuService.registCategoryAndMenu2(categoryDTO, menuDTO);

        if(insertResult == 1) {
            System.out.println("성공");
        }else {
            System.out.println("실실실실패");
        }

    }
}
