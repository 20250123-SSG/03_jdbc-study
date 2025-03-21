package com.minkook.run;

import com.minkook.model.dao.MenuDao;
import com.minkook.model.dto.CategoryDTO;
import com.minkook.model.dto.MenuDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static com.minkook.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {
        /*
            메뉴등록
            1. 마지막메뉴번호 조회
            2. 카테코리 전체 정보 조회
            3. 메뉴 등록
         */

        Connection conn = getConnection();
        try {
            System.out.println(conn.getAutoCommit());
        }catch (SQLException e){
            e.printStackTrace();
        }
        MenuDao dao = new MenuDao();

        //1. 마지막 메뉴 번호 조회
        //int lastMenuCode = dao.selectLastMenuCode(conn);

        //2. 카테고리 전체 정보 조회
        //List<CategoryDTO> list = dao.selectCategoryList(conn);

        //등록할 메뉴 정보 입력
        MenuDTO menu = new MenuDTO();

        Scanner sc = new Scanner(System.in);
        System.out.print("등록할 메뉴명: ");
        menu.setMenuName(sc.nextLine());
        System.out.print("등록할 메뉴가격: ");
        menu.setMenuPrice(sc.nextInt());
        System.out.print("등록할 메뉴카테고리번호: ");
        menu.setCategoryCode(sc.nextInt());
        sc.nextLine();
        System.out.print("판매메뉴로 적용하시겠습니까(y/n): ");
        menu.setOrderableStatus(sc.nextLine().toUpperCase());

        //3. 메뉴등록
        int result = dao.insertMenu(conn,menu);
        if(result > 0){
            System.out.println("등록성공");
        }else {
            System.out.println("등록실패");
        }
        
    }
}
