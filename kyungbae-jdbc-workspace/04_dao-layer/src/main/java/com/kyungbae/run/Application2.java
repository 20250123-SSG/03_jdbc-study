package com.kyungbae.run;

import com.kyungbae.model.dao.MenuDAO;
import com.kyungbae.model.dto.MenuDTO;

import java.sql.Connection;
import java.util.Scanner;

import static com.kyungbae.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {
        /*
            메뉴 등록
            1. 마지막 메뉴 번호(현존하는 메뉴 번호의 최대값) 조회하기 (select)
            2. 카테고리 전체 정보 조회하기 (select)
            3. 사용자에게 값을 입력받아 메뉴 등록하기 (insert)
         */
        MenuDAO menuDAO = new MenuDAO();
        Connection conn = getConnection();

        // 1. 마지막 메뉴 번호 조회
        int lastMenuCode = menuDAO.selectLastMenuCode(conn);

        // 2. 카테고리 전제 정보 조회
        menuDAO.selectCategoryList(conn).forEach(System.out::println);

        // 등록할 메뉴 입력
        Scanner sc = new Scanner(System.in);
        MenuDTO menu = new MenuDTO();
        menu.setMenuCode(lastMenuCode + 1); // 1 에서 불러온 마지막 메뉴번호 +1
        System.out.print("등록할 메뉴 명 : ");
        menu.setMenuName(sc.nextLine());
        System.out.print("등록할 메뉴 가격 : ");
        menu.setMenuPrice(sc.nextInt());
        System.out.print("카테고리 번호 : ");
        menu.setCategoryNo(sc.nextInt());
        sc.nextLine();
        System.out.print("판매가능 여부(y/n) : ");
        menu.setOrderable(sc.nextLine().toUpperCase());

        // 3. 메뉴 등록
        if (menuDAO.insertMenu(conn, menu) > 0) {
            System.out.println("메뉴 삽입 완료");
        } else {
            System.out.println("실패");
        }

    }
}
