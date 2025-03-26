package com.sotogito.run;

import com.sotogito.model.dao.MenuDAO;
import com.sotogito.model.dto.CategoryDTO;
import com.sotogito.model.dto.MenuDTO;
import com.sotogito.model.service.MenuService;

import java.sql.Connection;

import static com.sotogito.common.JDBCTemplate.*;

public class Application4 {
    public static void main(String[] args) {
        String categoryName = "기타";
        Integer refCategoryCode = null;

        String menuName = "개구리뒷다리살2";
        int menuPrice = 7000;
        int categoryCode = 4;
        String orderableStatus = "Y";

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName(categoryName);
        categoryDTO.setRefCategoryCode(refCategoryCode);

        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setMenuName(menuName);
        menuDTO.setMenuPrice(menuPrice);
        menuDTO.setCategoryCode(categoryCode);
        menuDTO.setOrderableStatus(orderableStatus);

        MenuDAO menuDAO = new MenuDAO();
        Connection conn = getConnection();

        int registResult =  new MenuService().registCategoryAndMenu(categoryDTO,menuDTO);

        if(registResult == 1) {
            System.out.println("성공");
        }else {
            System.out.println("실패");
        }
    }
}
