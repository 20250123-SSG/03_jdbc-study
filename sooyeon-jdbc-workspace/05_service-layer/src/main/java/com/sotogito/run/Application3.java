package com.sotogito.run;

import com.sotogito.model.dao.MenuDAO;
import com.sotogito.model.dto.CategoryDTO;
import com.sotogito.model.dto.MenuDTO;

import java.sql.Connection;

import static com.sotogito.common.JDBCTemplate.*;

public class Application3 {
    public static void main(String[] args) {
        /// 신규카테고리와 신규메뉴를 동시에 추가

        String categoryName = "기타";
        Integer refCategoryCode = null;

        String menuName = "개구리뒷다리살2";
        int menuPrice = 7000;
        int categoryCode = 0;
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


        /// 현재 생성된 Connection을 공유하면 하나의 트랜잭션으로 묶임
        int categoryInsertResult = menuDAO.insertCategory(conn, categoryDTO);
        int menuInsertResult = menuDAO.insertMenu(conn, menuDTO);


        //트랜젝션 처리
        if(categoryInsertResult == 1 && menuInsertResult == 1) {
            commit(conn);
        }else {
            rollback(conn);
        }

        ///  Caused by: java.sql.SQLIntegrityConstraintViolationException: Cannot add or update a child row: a foreign key constraint fails (`menudb`.`tbl_menu`, CONSTRAINT `fk_category_code` FOREIGN KEY (`category_code`) REFERENCES `tbl_category` (`category_code`))
        /*
            카테고리 insert -- 성공
            메뉴 insert -- 실패
            ------------------------------> 최종 실패 -> rollback
         */

    }
}
