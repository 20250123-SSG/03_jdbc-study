package com.kyungbae.run;

import com.kyungbae.model.dao.MenuDAO;
import com.kyungbae.model.dto.CategoryDTO;
import com.kyungbae.model.dto.MenuDTO;

import java.sql.Connection;

import static com.kyungbae.common.JDBCTemplate.*;

public class Application3 {
    public static void main(String[] args) {
        // 신규 카테고리와 메뉴 동시에 추가하기
        // (둘다 추가가 잘 되어야만 성공)

        // 사용자에게 값 입력받기 (입력받았다고 가정)
        String categoryName = "기타";
        Integer refCategoryCode = null;

        String menuName = "개구리뒷다리살";
        int menuPrice = 7000;
        int categoryCode = 0; // 외래키 제약조건 위배
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

        // Connection 객체를 같이 사용하면 하나의 trasaction으로 묶인다.

        Connection conn = getConnection();
        MenuDAO dao = new MenuDAO();
        int result1 = dao.insertCategory(conn, category);
        int result2 = dao.insertMenu(conn, menu);

        if (result1 > 0 && result2 > 0) {
            commit(conn);
            System.out.println("신규 카테고리 및 메뉴등록 성공");
        } else {
            rollback(conn);
            System.out.println("신규 카테고리 및 메뉴등록 실패");
        }
        close(conn);

        /*
            trasaction-----------
            카테고리 insert 성공
            메뉴 insert 실패
            ------------ rollback
         */

    }
}
