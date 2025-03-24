package com.johnth.run;

import com.johnth.model.dto.CategoryDTO;
import com.johnth.model.dto.MenuDTO;

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

import static com.johnth.common.JDBCTemplate.close;
import static com.johnth.common.JDBCTemplate.getConnection;

/*
    # 메뉴 등록
    1. 마지막 메뉴번호 조회
    2. 카테고리 정보 조회
    3. 메뉴 등록
 */
public class Application1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/johnth/mapper/menu_query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Connection conn = getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        int lastMenuCode = 0;

        String query = prop.getProperty("selectLastMenuCode");

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
        }

        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        List<CategoryDTO> list = new ArrayList<CategoryDTO>();

        String query2 = prop.getProperty("selectCategoryList");

        try {
            ps2 = conn.prepareStatement(query2);
            rs2 = ps2.executeQuery();
            while (rs2.next()){
                CategoryDTO category = new CategoryDTO();
                category.setCategoryCode(rs2.getInt("category_code"));
                category.setCategoryName(rs2.getString("category_name"));
                list.add(category);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            close(rs2);
            close(ps2);
        }

        for (CategoryDTO category : list) {
            System.out.println(category);
        }

        MenuDTO menu = new MenuDTO();
        menu.setMenu_code(lastMenuCode + 1);
        System.out.print("등록할 메뉴명: ");
        menu.setMenu_name(sc.nextLine());
        System.out.print("등록할 메뉴가격: ");
        menu.setMenu_price(sc.nextInt());
        System.out.print("등록할 메뉴 카테고리 번호: ");
        menu.setCategory_code(sc.nextInt());
        sc.nextLine();
        System.out.print("판매 메뉴로 적용(Y/N): ");
        menu.setOrderable_status(sc.nextLine().toUpperCase());

        PreparedStatement ps3 = null;
        int result = 0;
        String query3 = prop.getProperty("insertMenu");

        try {
            ps3 = conn.prepareStatement(query3);
            ps3.setInt(1,menu.getMenu_code());
            ps3.setString(2,menu.getMenu_name());
            ps3.setInt(3,menu.getMenu_price());
            ps3.setInt(4,menu.getCategory_code());
            ps3.setString(5,menu.getOrderable_status());

            result = ps3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs2);
            close(ps3);
            close(conn);
        }
        
        if(result >0){
            System.out.println("등록성공");
        } else{
            System.out.println("등록실패");
        }
    }
}
