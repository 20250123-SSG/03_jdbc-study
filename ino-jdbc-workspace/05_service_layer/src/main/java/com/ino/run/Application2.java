package com.ino.run;

import com.ino.model.dao.MenuDAO;
import com.ino.model.dto.MenuDTO;

import java.sql.Connection;
import java.sql.SQLException;

import static com.ino.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {
        String menuName = "도가니탕수육";
        int menuPrice = 40000;
        int categoryCode = 5;
        String orderableStatus = "N";

        MenuDTO menu = new MenuDTO(menuName, menuPrice, categoryCode, orderableStatus);
        Connection conn = getConnection();
        MenuDAO dao = new MenuDAO();
        try {
            System.out.println("autoCommit: " + conn.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int result = dao.insertMenu(conn, menu);

        if (result > 0) {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.commit();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        if (result > 0) {
            System.out.println("menu insert completed");
        } else {
            System.out.println("menu insert failed");
        }

    }
}
