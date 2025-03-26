package com.ino.model.dao;

import com.ino.model.dto.CategoryDTO;
import com.ino.model.dto.MenuDTO;

import java.sql.Connection;

import static com.ino.common.JDBCTemplate.*;

public class MenuService {
    public int registCategoryAndMenu2(CategoryDTO category, MenuDTO menu) {

        int result = 0;
        MenuDAO menuDAO = new MenuDAO();

        Connection conn = getConnection();

        int result1 = menuDAO.insertCategory(conn, category);
        int currCategoryCode = menuDAO.selectCurrentCategoryCode(conn);
        menu.setCategoryCode(currCategoryCode);
        int result2 = menuDAO.insertMenu(conn, menu);

        if(result1 > 0 && result2 >0) {
            commit(conn);
            result = 1;
        }else{
            rollback(conn);
        }
        return result;
    }
}
