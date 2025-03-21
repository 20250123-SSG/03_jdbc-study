package com.sotogito.section02.update;

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

public class Application {
    /**
     * 1. 수정할 메뉴 정보 입력받기
     * 2. 입력값을 전송시티기 위한 dto에 데이터 담기
     * 3. update문 진행
     * 4. 응답메시지
     * @param args
     */
    public static void main(String[] args) {
        /// 수정할 메누정보 입력
        Scanner sc = new Scanner(System.in);
        System.out.println("변경하고싶은 메뉴 번호와, 변경할 메뉴명과 가격을 입력하세요");
        int editMenuCode = Integer.parseInt(sc.nextLine());
        String newMenuName = sc.nextLine();
        int newMenuPrice = Integer.parseInt(sc.nextLine());

        MenuDTO dto = new MenuDTO();
        dto.setMenuCode(editMenuCode);
        dto.setMenuName(newMenuName);
        dto.setMenuPrice(newMenuPrice);


        Connection conn = getConnection();
        PreparedStatement ps = null;
        int result = 0;

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/sotogito/mapper/menu-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String updateQuery = prop.getProperty("updateMenu");
        try {
            ps = conn.prepareStatement(updateQuery);
            ps.setString(1, dto.getMenuName());
            ps.setInt(2, dto.getMenuPrice());
            ps.setInt(3, dto.getMenuCode());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(ps);
            close(conn);
        }

        if(result > 0) {
            System.out.println("변경 성공");
        }else {
            System.out.println("변경 실패");
        }
    }
}
