package com.jjanggu.run;

import com.jjanggu.model.dao.MenuDAO;
import com.jjanggu.model.dto.CategoryDTO;
import com.jjanggu.model.dto.MenuDTO;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import static com.jjanggu.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {
        /*
            메뉴등록
            1. 마지막 메뉴 번호 조히
            2. 카테고리 전체 정보 조회
            3. 메뉴 등록
         */

        Connection conn = getConnection();
        MenuDAO dao = new MenuDAO(); // prop이 세팅됨

        // 1. 마지막 메뉴 번호 조회
        int lastMenuCode = dao.selectLastMenuCode(conn);

        // 2. 카테고리 전체 정보 조회
        List<CategoryDTO> list = dao.selectCategoryList(conn);

        for(CategoryDTO cate : list){
            System.out.println(cate.getCategoryCode() + " : " + cate.getCategoryName());
        }

        // 등록할 메뉴 정보 입력
        MenuDTO menu = new MenuDTO(); // 등록할 메뉴 정보를 담기 위한 객체
        menu.setMenuCode(lastMenuCode + 1);

        Scanner sc = new Scanner(System.in);
        System.out.print("등록할 메뉴명: ");
        menu.setMenuName(sc.nextLine());
        System.out.print("등록할 메뉴가격: ");
        menu.setMenuPrice(sc.nextInt());
        System.out.print("등록할 카테고리번호");
        menu.setCategoryCode(sc.nextInt());
        sc.nextLine();
        System.out.print("판매 메뉴로 적용하시겠습니까(y/n): ");
        menu.setOrderableStatus(sc.nextLine().toUpperCase());

        // 3. 메뉴 등록
        int result = dao.insertMenu(conn, menu);

        if(result > 0){
            System.out.println("등록 성공");
        }else {
            System.out.println("등록 실패");
        }
    }
}
