package com.minkook.run;

import com.minkook.model.dto.CategoryDTO;
import com.minkook.model.dto.MenuDTO;

import java.awt.*;
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

import static com.minkook.common.JDBCTemplate.close;
import static com.minkook.common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String [] args){
        /*
            메뉴 등록
            1. 마지막 메뉴번호(현존하는 메뉴 번호의 최대갑)
            2. 카테고리 전체 정보 조회해오기(select)
            3. 사용자에게 값을 입력받아 메뉴 등록하기(insert)
         */

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/minkook/mapper/menu-query.xml"));
        }catch (IOException e){
            e.printStackTrace();
        }

        Connection conn = getConnection();

        //1. 마지막 메뉴 번호 조회해오기 -----------------------------------------
        PreparedStatement pstmt1 = null;
        ResultSet rset1 = null;

        int lastMenuCode = 0; //최종 결과를 담을 변수

        String query1 = prop.getProperty("selectlastMenuCode");


        try {
            pstmt1 = conn.prepareStatement(query1);
            rset1 = pstmt1.executeQuery();
            if(rset1.next()){
                lastMenuCode = rset1.getInt("last_menu_code");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rset1);
            close(pstmt1);
        }
        
        // -------------------------------------
        
        //2. 카테로리 전체 정보 조회해오기
        PreparedStatement pstmt2 = null;
        ResultSet rset2 = null;

        List<CategoryDTO> list = new ArrayList<>();

        String query2 = prop.getProperty("selectlastMenuList");

        try{
            pstmt2 = conn.prepareStatement(query2);
            rset2 = pstmt2.executeQuery();

            while (rset2.next()){
                CategoryDTO category = new CategoryDTO();
                category.setCategoryCode(rset2.getInt("category_code"));
                category.setCategoryName(rset2.getString("category_name"));
                list.add(category);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rset2);
            close(pstmt2);
        }

        //------------------------------------------------------------\
        for(CategoryDTO cate : list){
            System.out.println(cate);
        }

        //등록할 메뉴 정보 입력
        MenuDTO menu = new MenuDTO();
        menu.setMenuCode(lastMenuCode+1);

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


        //3.메뉴등록
        PreparedStatement pstmt3 = null;

        int result = 0;

        String query3 = prop.getProperty("insertMenu");

        try {
            pstmt3 = conn.prepareStatement(query3);
            pstmt3.setInt(1, menu.getMenuCode());
            pstmt3.setString(2, menu.getMenuName());
            pstmt3.setInt(3, menu.getMenuPrice());
            pstmt3.setInt(4,menu.getCategoryCode());
            pstmt3.setString(5,menu.getOrderableStatus());
            result = pstmt3.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(pstmt3);
            close(conn);
        }
        //------------------------------------------------------------\

        if(result > 0){
            System.out.println("등록 성공");
        }else {
            System.out.println("등록 실패");
        }

    }
}
