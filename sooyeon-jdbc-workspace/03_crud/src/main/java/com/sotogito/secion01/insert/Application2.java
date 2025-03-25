package com.sotogito.secion01.insert;

import com.sotogito.common.JDBCTemplate;
import com.sotogito.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.sotogito.common.JDBCTemplate.close;
import static com.sotogito.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {

        ///  추가할 메뉴 정보 입력받기
        Scanner sc = new Scanner(System.in);
        System.out.println("메뉴명");
        String menuName = sc.nextLine();
        System.out.println("가격");
        int menuPrice = Integer.parseInt(sc.nextLine());
        System.out.println("카테고리번호");
        int categoryId = Integer.parseInt(sc.nextLine());
        System.out.println("판매여부 결정 y/n");
        String orderableStatus = sc.nextLine().toUpperCase();

        MenuDTO newMenu = new MenuDTO();
        newMenu.setMenuName(menuName);
        newMenu.setMenuPrice(menuPrice);
        newMenu.setCategoryCode(categoryId);
        newMenu.setOrderableStatus(orderableStatus);


        ///  insert문 진행
        Connection conn = getConnection();
        PreparedStatement ps = null;
        int result = 0; /// 조회 데이터가 아니라 ResultSet은 필요 없지만 insert의 성공 여부에 따른 처리된 row정수값을 받는다.

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/sotogito/mapper/menu-query.xml"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String query = prop.getProperty("insertMenu");
        try {
            ps = conn.prepareStatement(query);
            ///  파라미터 바인딩
            ps.setString(1, newMenu.getMenuName());
            ps.setInt(2, newMenu.getMenuPrice());
            ps.setInt(3, newMenu.getCategoryCode());
            ps.setString(4, newMenu.getOrderableStatus());
            
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(ps);
            close(conn);
        }

        if (result <= 0) {
            System.out.println("출력 실패");
            return;
        }
        System.out.println("출력성공");
    }

}
