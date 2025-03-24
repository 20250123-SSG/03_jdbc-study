package com.johnth.run;

import com.johnth.model.dao.MenuDAO;
import com.johnth.model.dto.CategoryDTO;
import com.johnth.model.dto.MenuDTO;

import java.sql.Connection;

import static com.johnth.common.JDBCTemplate.*;

public class Application3 {
    public static void main(String[] args) {
        String categoryName = "기타";
        Integer refCategoryCode = null;
        String menuName = "밥";
        int menuPrice = 100000;
        int categoryCode = 0;
        String orderableStatus = "Y";

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName(categoryName);
        categoryDTO.setRefCategoryCode(refCategoryCode);

        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setMenu_name(menuName);
        menuDTO.setMenu_price(menuPrice);
        menuDTO.setCategory_code(categoryCode);
        menuDTO.setOrderable_status(orderableStatus);

        MenuDAO menuDAO = new MenuDAO();
        Connection conn = getConnection();

        int result = menuDAO.insertCategory(conn, categoryDTO);
        int result2 = menuDAO.insertMenu(conn, menuDTO);

        if(result > 0 && result2 > 0) {
            commit(conn);
        } else{
            rollback(conn);
        }

        if(result > 0 && result2 > 0) {
            System.out.println("등록 성공");
        } else {
            System.out.println("등록 실패");
        }
    }
}
