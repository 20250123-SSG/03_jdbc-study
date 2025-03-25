package com.sotogito.section03.delete;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.sotogito.common.JDBCTemplate.close;
import static com.sotogito.common.JDBCTemplate.getConnection;

public class Application {
    /**
     * 1. 삭제할메뉴 번호 입력받기
     * 2. delete 수정
     * 3. 응답메시지
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("삭제할 메뉴 코드");
        int menuCode = Integer.parseInt(sc.nextLine());

        Connection conn = getConnection();
        PreparedStatement ps = null;
        int result = 0;

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/sotogito/mapper/menu-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String query = prop.getProperty("deleteMenu");
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, menuCode);

            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(ps);
            close(conn);

        }

        if(result > 0) {
            System.out.println("삭제 성공");
        }else {
            System.out.println("삭제 실패");
        }
    }
}
