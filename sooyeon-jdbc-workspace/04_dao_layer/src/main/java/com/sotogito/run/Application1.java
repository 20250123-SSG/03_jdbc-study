package com.sotogito.run;

import com.sotogito.model.dto.CategoryDTO;
import com.sotogito.model.dto.MenuDTO;

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

import static com.sotogito.common.JDBCTemplate.close;
import static com.sotogito.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        /**
         * ### 메뉴 등록
         * 1. 마지막 메뉴 번호조회 - 현조하는 메뉴 번호의 가장 큰 값(select)
         * 2. 카테고리 전체 정보 조회해오기 (select)
         * 3. 사용자에게 값을 입력받아서 메뉴 등록하기 (insert)
         */

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/sotogito/mapper/menu-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Connection conn = getConnection();


        /// 1. 마지막 번호 조회해오기
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        int lastMenuCode = 0;

        String maxMenuCodeQuery = prop.getProperty("selectLastMenuCode");

        try {
            ps1 = conn.prepareStatement(maxMenuCodeQuery);
            rs1 = ps1.executeQuery();
            if (rs1.next()) {
                lastMenuCode = rs1.getInt("last_menu_code"); // 별칭으
                System.out.println(lastMenuCode);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(rs1);
            close(ps1);
            // close(conn);db 연결은 아직 안 끊음
        }




        /// 2. 카테고리 전체 정보 조회
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        List<CategoryDTO> categoryDtoResult = new ArrayList<>();

        String categoryQuery = prop.getProperty("selectCategoryList");

        try {
            ps2 = conn.prepareStatement(categoryQuery);
            rs2 = ps2.executeQuery();

            while (rs2.next()) {
                CategoryDTO categoryDto = new CategoryDTO();
                categoryDto.setCategoryCode(rs2.getInt("category_code"));
                categoryDto.setCategoryName(rs2.getString("category_name"));

                categoryDtoResult.add(categoryDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(rs2);
            close(ps2);
        }


        categoryDtoResult.forEach(System.out::println);


        /// 등록할 메뉴 정보 입력
        MenuDTO menuDto = new MenuDTO(); /// 등록할 메뉴 정보를 담기 위한 객체
        menuDto.setMenuCode(lastMenuCode + 1);

        Scanner sc = new Scanner(System.in);
        System.out.println("등록할 메뉴명,가격,카테고리코드,판매여부 입력");
        menuDto.setMenuName(sc.nextLine());
        menuDto.setMenuPrice(Integer.parseInt(sc.nextLine()));
        menuDto.setCategoryCode(Integer.parseInt(sc.nextLine()));
        menuDto.setOrderableStatus(sc.nextLine().toUpperCase());

        /// 3. 메뉴 등록
        PreparedStatement ps3 = null;
        int insertResult = 0;

        String insertQuery = prop.getProperty("insertMenu");
        try {
            ps3 = conn.prepareStatement(insertQuery);
            ps3.setInt(1, menuDto.getMenuCode());
            ps3.setString(2, menuDto.getMenuName());
            ps3.setInt(3, menuDto.getMenuPrice());
            ps3.setInt(4, menuDto.getCategoryCode());
            ps3.setString(5, menuDto.getOrderableStatus());

            insertResult = ps3.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(ps3);
            close(conn);
        }

        if(insertResult > 0) {
            System.out.println("성공");
        }else {
            System.out.println("실패");
        }
    }
}
