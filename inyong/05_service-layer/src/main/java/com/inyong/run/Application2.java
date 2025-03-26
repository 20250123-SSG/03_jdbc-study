package com.inyong.run;

import com.inyong.model.dao.MenuDAO;
import com.inyong.model.dto.MenuDTO;

import java.sql.Connection;
import java.sql.SQLException;

import static com.inyong.common.JDBCTemplate.*;

public class Application2 {
    public static void main(String[] args) {

        // 신규 메뉴 등록 요청해보기

        // 사용자에게 값 입력받기 (입력받았다 가정)
        String menuName = "도가니탕수육";
        int menuPrice = 40000;
        int categoryCode = 5;
        String orderableStatus = "N";

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
        int result = dao.insertMenu(conn, menu); // insert문 수행하고 돌아옴

        if(result > 0){ // 성공적으로 수행되었을 경우
            /*
            try {
                if(conn != null && !conn.isClosed()){
                    conn.commit();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
             */
            commit(conn);
        }else{ // 그게 아닐 경우
            /*
            try {
                if(conn != null && !conn.isClosed()){
                    conn.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
             */
            rollback(conn);
        }

        // 결과를 가지고 응답메세지 출력
        if(result > 0){
            System.out.println("메뉴 등록 성공");
        }else{
            System.out.println("메뉴 등록 실패");
        }
    }
}
