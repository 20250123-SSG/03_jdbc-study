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

}
