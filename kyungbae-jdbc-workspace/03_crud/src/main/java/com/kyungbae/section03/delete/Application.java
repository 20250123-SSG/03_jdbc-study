package com.kyungbae.section03.delete;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.kyungbae.common.JDBCTemplate.close;
import static com.kyungbae.common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {

        // 삭제할 메뉴 번호 입력받기
        Scanner sc = new Scanner(System.in);
        System.out.print("삭제할 메뉴 번호 : ");
        int menuCode = sc.nextInt();

        // delete문 수행
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/kyungbae/mapper/menu-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String query = prop.getProperty("deleteMenu");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, menuCode);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }

        // 응답결과 출력
        if (result > 0) {
            System.out.println("삭제가 완료되었습니다.");
        } else {
            System.out.println("삭제할 항목을 찾지 못했습니다.");
        }

    }
}
