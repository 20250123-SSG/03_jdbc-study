package com.kyungbae.section01.insert;

import com.kyungbae.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.kyungbae.common.JDBCTemplate.close;
import static com.kyungbae.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {

        // 추가할 메뉴정보 입력받기
        Scanner sc = new Scanner(System.in);
        System.out.print("메뉴명 : ");
        String menuName = sc.nextLine();
        System.out.print("가격 : ");
        int menuPrice = sc.nextInt();
        System.out.print("카테고리 : ");
        int categoryNo = sc.nextInt();
        sc.nextLine();
        System.out.print("판매여부 (y/n) : ");
        String orderable = sc.nextLine().toUpperCase();

        // 메뉴 정보 MenuDTO에 기입
        MenuDTO newMenu = new MenuDTO();
        newMenu.setMenuName(menuName);
        newMenu.setMenuPrice(menuPrice);
        newMenu.setCategoryNo(categoryNo);
        newMenu.setOrderable(orderable);

        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        int result = 0; // 최종 결과를 기록할 변수
        // **executeUpdate 의 return 값은 rows를 가리키는 int 값

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/kyungbae/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // xml 파일에 저장해둔 실행할 쿼리문 불러오기
        String query = prop.getProperty("insertMenu");

        try {
            int num = 0;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(++num, newMenu.getMenuName());
            pstmt.setInt(++num, newMenu.getMenuPrice());
            pstmt.setInt(++num, newMenu.getCategoryNo());
            pstmt.setString(++num, newMenu.getOrderable());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }

        if (result > 0) {
            System.out.println("메뉴 등록이 완료되었습니다.");
        } else {
            System.out.println("메뉴 등록 실패");
        }
    }
}
