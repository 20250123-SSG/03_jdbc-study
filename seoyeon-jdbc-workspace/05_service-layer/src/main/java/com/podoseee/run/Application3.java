package com.podoseee.run;

import com.podoseee.model.dao.MenuDAO;
import com.podoseee.model.dto.CategoryDTO;
import com.podoseee.model.dto.MenuDTO;

import java.sql.Connection;

import static com.podoseee.common.JDBCTemplate.*;

public class Application3 {
    public static void main(String[] args) {
        // 신규 카테고리와 메뉴 동시에 추가하기 (둘다 추가가 잘 되어야만 성공)

        // 사용자에게 값 입력 (입력받았다고 가정)
        String categoryName = "기타";
        Integer refCategoryCode = null;

        String menuName = "개구리뒷다리살";
        int menuPrice = 7000;
        int categoryCode = 0;
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

        MenuDAO dao = new MenuDAO();

        Connection conn = getConnection();

        // 현재 생성된 Connection를 공유하면 하나의 트랜잭션으로 묶임
        int result1 = dao.insertCategory(conn, category); // 카테고리 추가
        int result2 = dao.insertMenu(conn, menu); // 메뉴 추가

        // 트랜잭션 처리
        if(result1 > 0 && result2 > 0){
            commit(conn);
        }else{
            rollback(conn);
        }

        // 성공실패 판별 후 응답메세지 출력
        if(result1 > 0 && result2 > 0){
            System.out.println("신규 카테고리와 메뉴 등록 성공");
        }else{
            System.out.println("신규 카테고리와 메뉴 등록 실패");
        }

        //  트랜잭션 ------------
        //  카테고리 insert 성공
        //  메뉴 insert 실패
        // ----------------------
        // => rollback 진행

    }
}