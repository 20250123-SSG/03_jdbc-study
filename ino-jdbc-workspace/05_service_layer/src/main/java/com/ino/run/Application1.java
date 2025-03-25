package com.ino.run;

import com.ino.model.dao.MenuDAO;
import com.ino.model.dto.MenuDTO;

import java.sql.Connection;

import static com.ino.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        String menuName = "정어리비빔밥";
        int menuPrice = 50000;
        int categoryCode = 4;
        String orderableStatus = "Y";

        MenuDTO menu = new MenuDTO(menuName, menuPrice, categoryCode, orderableStatus);
        Connection conn = getConnection();
        MenuDAO dao = new MenuDAO();
        int result = dao.insertMenu(conn, menu);

        if(result>0) {
            System.out.println("menu insert completed");
        }else {
            System.out.println("menu insert failed");
        }

    }
}
