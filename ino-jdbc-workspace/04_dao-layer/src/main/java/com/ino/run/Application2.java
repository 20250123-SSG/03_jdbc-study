package com.ino.run;

import com.ino.model.dao.MenuDAO;
import com.ino.model.dto.MenuDTO;

import java.sql.Connection;
import java.util.Scanner;

import static com.ino.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {
        MenuDAO menuDAO = new MenuDAO();
        Connection conn = getConnection();
        MenuDTO menu = new MenuDTO();
        Scanner sc = new Scanner(System.in);
        System.out.print("insert menuName: ");
        menu.setMenuName(sc.nextLine());
        System.out.print("insert menuPrice: ");
        menu.setMenuPrice(sc.nextInt());
        System.out.print("insert category number: ");
        menu.setCategoryCode(sc.nextInt());
        sc.nextLine();
        System.out.println("insert orderable status: ");
        menu.setOrderableStatus(sc.nextLine());
        menuDAO.insertMenu(conn, menu);
    }
}
