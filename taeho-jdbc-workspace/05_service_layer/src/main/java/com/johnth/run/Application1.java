package com.johnth.run;

import com.johnth.model.dao.MenuDAO;
import com.johnth.model.dto.MenuDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static com.johnth.common.JDBCTemplate.*;

public class Application1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        MenuDTO menuDTO = new MenuDTO();
        MenuDAO menuDAO = new MenuDAO();
        Connection conn = getConnection();
        try{
            System.out.println("auto commit 상태 : " + conn.getAutoCommit());
        } catch (SQLException e){
            e.printStackTrace();
        }

        System.out.print("등록할 메뉴명: ");
        menuDTO.setMenu_name(sc.nextLine());
        System.out.print("등록할 메뉴가격: ");
        menuDTO.setMenu_price(sc.nextInt());
        System.out.print("등록할 메뉴 카테고리 번호: ");
        menuDTO.setCategory_code(sc.nextInt());
        sc.nextLine();
        System.out.print("판매 메뉴로 적용(Y/N): ");
        menuDTO.setOrderable_status(sc.nextLine().toUpperCase());

        int result = menuDAO.insertMenu(conn, menuDTO);
        
        if (result > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
    }
}
