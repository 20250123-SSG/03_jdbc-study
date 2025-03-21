package com.jjanggu.run;

import com.jjanggu.model.dao.MenuDAO;
import com.jjanggu.model.dto.MenuDTO;

import java.sql.Connection;
import java.sql.SQLException;

import static com.jjanggu.common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {
        // 신슈 메뉴 등록 요청해보기

        // 사용자에게 값 입력받기
        String menuName = "정어리비빔밥";
        int menuPrice = 50000;
        int categoryCode = 4;
        String orderableStatus = "Y";
        // DTO객체에 사용자 입력값 담기
        MenuDTO menu = new MenuDTO();
        menu.setMenuName(menuName);
        menu.setMenuPrice(menuPrice);
        menu.setCategoryCode(categoryCode);
        menu.setOrderableStatus(orderableStatus);

        // sql문 실행해주는 DAO측 메소드 호출 후 결과받기
        Connection conn = getConnection();
        try {
            System.out.println("autoCommit상태: " + conn.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        MenuDAO dao = new MenuDAO();
        int result = dao.insertMenu(conn, menu);

        // 결과를 가지고 응답메세지 출력
        if(result > 0){
            System.out.println("메뉴 등록 성공");
        }else {
            System.out.println("메뉴 등록 실패");
        }
    }
}
