package com.sotogito.run;

import com.sotogito.model.dao.MenuDAO;
import com.sotogito.model.dto.MenuDTO;

import java.sql.Connection;
import java.sql.SQLException;

import static com.sotogito.common.JDBCTemplate.*;

public class Application2 {
    public static void main(String[] args) {

        String menuName = "도가니탕수육";
        int menuPrice = 40000;
        int categoryCode = 5;
        String orderableStatus = "N";

        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setMenuName(menuName);
        menuDTO.setMenuPrice(menuPrice);
        menuDTO.setCategoryCode(categoryCode);
        menuDTO.setOrderableStatus(orderableStatus);


        Connection conn = getConnection();
        try {
            System.out.println("autoCommit상태" + conn.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MenuDAO menuDAO = new MenuDAO();

        int insertResult = menuDAO.insertMenu(conn, menuDTO); ///autoCommit false여서 커밋해줘야됨


        if (insertResult == 1) {
            commit(conn);
            System.out.println("메뉴 등록 성공");
        } else {
            rollback(conn);
            System.out.println("실패");
        }

    }
}
