package com.johnth.service;

import com.johnth.model.dao.MenuDAO;
import com.johnth.model.dto.CategoryDTO;
import com.johnth.model.dto.MenuDTO;

import java.sql.Connection;

import static com.johnth.common.JDBCTemplate.*;

/*
    # Service
    1. 비지니스 로직 처리와 트랜잭션 관리를 담당
        - 사용자의 요청에 따라 순차적으로 실행해야되는 작업들을 하나로 묶어 관리
        - 중간과정에 문제 발생 시 rollback 진행으로 하나의 트랜잭션으로 묶어 관리
    2. 처리 과정
        1) Connection 생성
        2) 순차적으로 작업 실행
        3) 트랜잭션 처리가 필요한 경우 트랜잭션 처리
        4) Connection 반납

    * 비지니스 로직: DB 와 사용자 인터페이스(UI) 간의 정보교환을 위한 규칙이나 알고리즘
 */
public class MenuService {
    public int registCategoryAndMenu(CategoryDTO categoryDTO, MenuDTO menuDTO) {
        MenuDAO menuDAO = new MenuDAO();
        Connection conn = getConnection();

        int result = menuDAO.insertCategory(conn, categoryDTO);
        int result2 = menuDAO.insertMenu(conn, menuDTO);

        if (result > 1 && result2 > 1) {
            commit(conn);
            result = 1;
        } else {
            rollback(conn);
        }
        close(conn);

        return result;
    }

    // 신규 카테고리 등록 후 등록시 생성된 카테고리 번호로 메뉴 등록
    public int registCategoryAndMenuV2(CategoryDTO categoryDTO, MenuDTO menuDTO) {
        // 결과 판정값
        int result = 0;

        MenuDAO menuDAO = new MenuDAO();
        Connection conn = getConnection();

        int result1 = menuDAO.insertCategory(conn, categoryDTO);
        int currentCategoryCode = menuDAO.selectCurrentCategoryCode(conn);
        menuDTO.setCategory_code(currentCategoryCode);

        int result2 = menuDAO.insertMenu(conn, menuDTO);

        if (result1 > 1 && result2 > 1) {
            commit(conn);
            result = 1;
        } else {
            rollback(conn);
        }

        return result;
    }
}
