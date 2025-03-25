package com.johnth.run;

import com.johnth.model.dto.CategoryDTO;
import com.johnth.model.dto.MenuDTO;
import com.johnth.service.MenuService;

import java.util.Scanner;

public class Application5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("등록시킬 카테고리명");
        String categoryName = sc.nextLine();
        System.out.print("등록시킬 카테고리의 상위 카테고리 번호: ");
        Integer refCategoryCode = sc.nextInt();
        sc.nextLine();

        System.out.print("\n등록시킬 메뉴명: ");
        String menuName = sc.nextLine();
        System.out.print("등록시킬 메뉴 가격: ");
        int menuPrice = sc.nextInt();
        System.out.print("주문가능 여부: ");
        String orderableStatus = sc.nextLine().toUpperCase();

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName(categoryName);
        categoryDTO.setRefCategoryCode(refCategoryCode);

        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setMenu_name(menuName);
        menuDTO.setMenu_price(menuPrice);
        menuDTO.setOrderable_status(orderableStatus);

        MenuService menuService = new MenuService();
        menuService.registCategoryAndMenu(categoryDTO, menuDTO);

        int result = menuService.registCategoryAndMenuV2(categoryDTO,menuDTO);

        if (result > 0) {
            System.out.println("등록성공");

        } else{
            System.out.println("등록실패");
        }
    }
}
