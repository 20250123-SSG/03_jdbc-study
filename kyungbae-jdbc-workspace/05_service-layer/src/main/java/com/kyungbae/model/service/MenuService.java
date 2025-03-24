package com.kyungbae.model.service;

import com.kyungbae.model.dao.MenuDAO;
import com.kyungbae.model.dto.CategoryDTO;
import com.kyungbae.model.dto.MenuDTO;

import java.sql.Connection;

import static com.kyungbae.common.JDBCTemplate.*;

public class MenuService {

    // 신규 카테고리와 메뉴 동시 추가
    public boolean registCategoryAndMenu(CategoryDTO category, MenuDTO menu){
        Connection conn = getConnection();
        MenuDAO dao = new MenuDAO();
        int result1 = dao.insertCategory(conn, category);
        int result2 = dao.insertMenu(conn, menu);

        boolean result = false;
        if (result1 > 0 && result2 > 0) {
            commit(conn);
            result = true;
        } else {
            rollback(conn);
        }
        close(conn);
        return result;
    }

    public boolean registCategoryAndMenu2(CategoryDTO category, MenuDTO menu){
        // 신규 카테고리 등록 후 등록시 생성된 카테고리번호로 메뉴 등록
        boolean result = false;
        Connection conn = getConnection();
        MenuDAO menuDAO = new MenuDAO();

        // 1. 신규 카테고리 등록
        int categoryResult = menuDAO.insertCategory(conn, category);

        // 2. 1번 과정으로 등록된 카테고리 번호 조회
        int currentCategoryCode = menuDAO.selectCurrentCategoryCode(conn);

        // 3. 신규 메뉴 등록
        menu.setCategoryNo(currentCategoryCode);
        int menuResult = menuDAO.insertMenu(conn, menu);

        if (categoryResult > 0 && menuResult > 0) {
            commit(conn);
            result = true;
        } else {
            rollback(conn);
        }

        return result;
    }

}
