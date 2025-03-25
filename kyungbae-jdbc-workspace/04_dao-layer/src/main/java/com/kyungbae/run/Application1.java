package com.kyungbae.run;

import com.kyungbae.model.dto.CategoryDTO;
import com.kyungbae.model.dto.MenuDTO;

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

import static com.kyungbae.common.JDBCTemplate.close;
import static com.kyungbae.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        /*
            메뉴 등록
            1. 마지막 메뉴 번호(현존하는 메뉴 번호의 최대값) 조회하기 (select)
            2. 카테고리 전체 정보 조회하기 (select)
            3. 사용자에게 값을 입력받아 메뉴 등록하기 (insert)
         */

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/kyungbae/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Connection conn = getConnection();

        // 1. 마지막 메뉴번호 조회
        PreparedStatement pstmt1 = null;
        ResultSet rset1 = null;
        int lastMenuCode = 0;

        String query1 = prop.getProperty("selectLastMenuCode");
        try {
            pstmt1 = conn.prepareStatement(query1);
            rset1 = pstmt1.executeQuery();
            if (rset1.next()) {
                lastMenuCode = rset1.getInt("last_menu_code");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset1);
            close(pstmt1);
        }

        // 2. 카테고리 전체 정보 조회
        PreparedStatement pstmt2 = null;
        ResultSet rset2 = null;
        List<CategoryDTO> listCategory = new ArrayList<>();

        String query2 = prop.getProperty("selectCategoryList");
        try {
            pstmt2 = conn.prepareStatement(query2);
            rset2 = pstmt2.executeQuery();

            while (rset2.next()) {
                CategoryDTO category = new CategoryDTO(rset2.getInt("category_code"), rset2.getString("category_name"));
                listCategory.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset2);
            close(pstmt2);
        }

        listCategory.forEach(System.out::println);

        // 등록할 메뉴 정보 입력
        Scanner sc = new Scanner(System.in);
        MenuDTO menu = new MenuDTO();
        menu.setMenuCode(lastMenuCode + 1); // 1 에서 불러온 마지막 메뉴번호 +1
        System.out.print("등록할 메뉴 명 : ");
        menu.setMenuName(sc.nextLine());
        System.out.print("등록할 메뉴 가격 : ");
        menu.setMenuPrice(sc.nextInt());
        System.out.print("카테고리 번호 : ");
        menu.setCategoryNo(sc.nextInt());
        sc.nextLine();
        System.out.print("판매가능 여부(y/n) : ");
        menu.setOrderable(sc.nextLine().toUpperCase());

        // 3. 메뉴 등록
        PreparedStatement pstmt3 = null;
        int result = 0;

        String query3 = prop.getProperty("insertMenu");

        try {
            pstmt3 = conn.prepareStatement(query3);
            int num = 0;
            pstmt3.setInt(++num, menu.getMenuCode());
            pstmt3.setString(++num, menu.getMenuName());
            pstmt3.setInt(++num, menu.getMenuPrice());
            pstmt3.setInt(++num, menu.getCategoryNo());
            pstmt3.setString(++num, menu.getOrderable());

            result = pstmt3.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt3);
        }

        if (result > 0) {
            System.out.println("메뉴 등록 성공");
        } else {
            System.out.println("메뉴 등록 실패");
        }

    }
}
