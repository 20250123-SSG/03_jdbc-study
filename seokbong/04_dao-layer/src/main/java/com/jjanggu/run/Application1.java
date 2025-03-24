package com.jjanggu.run;

import com.jjanggu.model.dto.CategoryDTO;
import com.jjanggu.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static com.jjanggu.common.JDBCTemplate.close;
import static com.jjanggu.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        /*
            메뉴 등록
            1. 마지막 메뉴 번호(현존하는 메뉴 번호의 최대값) 조회해오기 (select)
            2. 카테고리 전체 정보 조회해오기 (select)
            3. 사용자에게 값을 입력받아 메뉴 등록하기 (insert)
         */

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/jjanggu/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Connection conn = getConnection();

        // 1.  마지막 메뉴 번호 조회해오기 -------------------------------------------
        PreparedStatement pstmt1 =  null;
        ResultSet rset1 = null;
        int lastMenuCode = 0; // 최종결과를 담을 변수

        String query1 = prop.getProperty("selectLastMenuCode");

        try {
            pstmt1 = conn.prepareStatement(query1);
            rset1 = pstmt1.executeQuery();
            if(rset1.next()){
                lastMenuCode = rset1.getInt("last_menu_code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset1);
            close(pstmt1);
        }
        //----------------------------------------------------------------------------------------

        // 2. 카테고리 전체 정보 조회해오기 ------------------------------------------------------
        PreparedStatement pstmt2 = null;
        ResultSet rset2 = null;
        List<CategoryDTO> list = new ArrayList<>(); // 최종 결과를 담을

        String query2 = prop.getProperty("selectCategoryList");

        try {
            pstmt2 = conn.prepareStatement(query2);
            rset2 = pstmt2.executeQuery();

            while (rset2.next()){
                CategoryDTO category = new CategoryDTO();
                category.setCategoryCode(rset2.getInt("category_code"));
                category.setCategoryName(rset2.getString("category_name"));
                list.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset2);
            close(pstmt2);
        }
        // --------------------------------------------------------------------------------

        for(CategoryDTO cate : list){
            System.out.println(cate);
        }

        // 등록할 메뉴 정보 입력
        MenuDTO menu = new MenuDTO(); // 등록할 메뉴 정보를 담기 위한 객체
        menu.setMenuCode(lastMenuCode + 1);

        Scanner sc = new Scanner(System.in);
        System.out.print("등록할 메뉴명: ");
        menu.setMenuName(sc.nextLine());
        System.out.print("등록할 메뉴가격: ");
        menu.setMenuPrice(sc.nextInt());
        System.out.print("등록할 카테고리번호");
        menu.setCategoryCode(sc.nextInt());
        sc.nextLine();
        System.out.print("판매 메뉴로 적용하시겠습니까(y/n): ");
        menu.setOrderableStatus(sc.nextLine().toUpperCase());

        // 3. 메뉴 등록 --------------------------------------------------------
        PreparedStatement pstm3 = null;
        int result = 0;

        String query3 = prop.getProperty("insertMenu");

        try {
            pstm3 = conn.prepareStatement(query3);
            pstm3.setInt(1, menu.getMenuCode());
            pstm3.setString(2, menu.getMenuName());
            pstm3.setInt(3, menu.getMenuPrice());
            pstm3.setInt(4, menu.getCategoryCode());
            pstm3.setString(5, menu.getOrderableStatus());

            result = pstm3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstm3);
        }
        // -------------------------------------------------------------------------------

        if(result > 0){
            System.out.println("등록 성공");
        }else {
            System.out.println("등록 실패");
        }

    }
}
