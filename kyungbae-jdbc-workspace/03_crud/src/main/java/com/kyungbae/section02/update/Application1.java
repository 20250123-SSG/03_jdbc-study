package com.kyungbae.section02.update;

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

public class Application1 {
    public static void main(String[] args) {

        // 수정할 메뉴 정보 입력
        Scanner sc = new Scanner(System.in);
        System.out.print("변경할 메뉴 번호 : ");
        int menuCode = sc.nextInt();
        sc.nextLine();
        System.out.print("변경할 메뉴 명 : ");
        String menuName = sc.nextLine();
        System.out.print("변경할 메뉴 금액 : ");
        int menuPirce = sc.nextInt();


        // 입력값을 전송시키기 위한 DTO 데이터 담기
        MenuDTO menu = new MenuDTO(menuCode, menuName, menuPirce);

        // update문 진행
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/kyungbae/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String query = prop.getProperty("updateMenu");

        try {
            int num = 0;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(++num, menu.getMenuName());
            pstmt.setInt(++num, menu.getMenuPrice());
            pstmt.setInt(++num, menu.getMenuCode());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }

        // 응답메세지 출력
        if (result != 0) {
            System.out.println("수정 완료되었습니다.");
        } else {
            System.out.println("수정되지 않았습니다.");
        }
    }
}
