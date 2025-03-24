package com.ino.run;

import com.ino.model.dao.MenuService;
import com.ino.model.dto.CategoryDTO;
import com.ino.model.dto.MenuDTO;

import java.util.Scanner;

public class Application5 {
    public static void main(String[] args) {
        MenuService menuService = new MenuService();



        Scanner sc = new Scanner(System.in);
        System.out.print("c-name : ");
        String categoryName = sc.nextLine();
        System.out.print("c-code: ");
        int categoryCode = sc.nextInt();
        sc.nextLine();
        System.out.print("m-name: ");
        String menuName = sc.nextLine();
        System.out.print("m-price");
        int menuPrice = sc.nextInt();
        sc.nextLine();
        System.out.print("m-orderable");
        String orderableStatus = sc.nextLine();
        CategoryDTO category = new CategoryDTO();
        category.setCategoryCode(categoryCode);
        category.setCategoryName(categoryName);

        MenuDTO menu = new MenuDTO();
        menu.setMenuName(menuName);
        menu.setMenuPrice(menuPrice);
        menu.setOrderableStatus(orderableStatus);
        int result = menuService.registCategoryAndMenu2(category, menu);
    }
}
