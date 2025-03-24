package com.kyungbae.run;

import com.kyungbae.model.dto.CategoryDTO;
import com.kyungbae.model.dto.MenuDTO;
import com.kyungbae.model.service.MenuService;

import java.util.Scanner;

public class Application5 {
    public static void main(String[] args) {

        // 신규카테고리 등록 후 생성된 카테고리번호로 메뉴 추가

        // 데이터 입력받기
        Scanner sc = new Scanner(System.in);
        // > 카테고리 정보 입력
        System.out.print("카테고리명 : ");
        String categoryName = sc.nextLine();
        System.out.print("상위 카테고리번호 : ");
        Integer refCategory = sc.nextInt();
        sc.nextLine();

        // > 메뉴 정보 입력
        System.out.print("\n메뉴 이름 : ");
        String menuName = sc.nextLine();
        System.out.print("메뉴 가격 : ");
        int menuPrice = sc.nextInt();
        sc.nextLine();
        System.out.print("판매가능여부 (y/n) : ");
        String orderableStatus = sc.nextLine().toUpperCase();

        CategoryDTO catDTO = new CategoryDTO();
        catDTO.setCategoryName(categoryName);
        catDTO.setRefCategoryCode(refCategory);

        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setMenuName(menuName);
        menuDTO.setMenuPrice(menuPrice);
        menuDTO.setOrderable(orderableStatus);

        // 비즈니스 로직을 처리할 서비스 호출
        MenuService ms = new MenuService();
        boolean result = ms.registCategoryAndMenu2(catDTO, menuDTO);

        // 반환받은 결과를 통해 응답 결정
        if (result) {
            System.out.println("등록 성공");
        } else {
            System.out.println("동록 실패");
        }

    }
}
