package com.johnth.run;

import com.johnth.model.dao.MenuDAO;
import com.johnth.model.dto.CategoryDTO;
import com.johnth.model.dto.MenuDTO;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import static com.johnth.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {

        Connection conn = getConnection();
        MenuDAO menuDAO = new MenuDAO();
        Scanner sc = new Scanner(System.in);

        int lastMenuCode = menuDAO.selectLastMenuCode(conn);

        menuDAO.selectCategoryList(conn);

        List<CategoryDTO> categoryList = menuDAO.selectCategoryList(conn);

        for (CategoryDTO category : categoryList) {
            System.out.println(category);
        }

        MenuDTO menuDTO = new MenuDTO();

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
        if(result >0){
            System.out.println("등록성공");
        } else{
            System.out.println("등록실패");
        }
    }
}
