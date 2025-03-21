package com.sotogito.run;

import com.sotogito.model.dao.MenuDAO;
import com.sotogito.model.dto.MenuDTO;

import java.sql.Connection;
import java.sql.SQLException;

import static com.sotogito.common.JDBCTemplate.getConnection;

public class Application1 {
    /**
     * 1. 신규 메뉴 등록 요청 시도
     * 2. 사용자에게 값 입력 받기
     * 3. DTO객체에 사용자 입력값 담기
     * 4. sql문 실행해주는 DAO측 메서드 호출 후 결과받기
     * 5. 결과 가지고 응답메세지 출력
     */
    public static void main(String[] args) {

        String menuName = "정어리비빔밥";
        int menuPrice = 50000;
        int categoryCode = 4;
        String orderableStatus = "Y";

        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setMenuName(menuName);
        menuDTO.setMenuPrice(menuPrice);
        menuDTO.setCategoryCode(categoryCode);
        menuDTO.setOrderableStatus(orderableStatus);


        Connection conn = getConnection();
        try {
            System.out.println("autoCommit상태" + conn.getAutoCommit());
            /**
             * autoCommit은 중간에 Rollback를 해야하는 상황을 위해 비활성화 해야한다.
             */
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MenuDAO menuDAO = new MenuDAO();

        int insertResult = menuDAO.insertMenu(conn, menuDTO);
        if (insertResult == 1) {
            System.out.println("메뉴 등록 성공");
        } else {
            System.out.println("실패");
        }
    }
}
