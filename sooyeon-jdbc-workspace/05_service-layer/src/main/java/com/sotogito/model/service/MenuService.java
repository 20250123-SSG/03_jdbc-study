package com.sotogito.model.service;

import com.sotogito.model.dao.MenuDAO;
import com.sotogito.model.dto.CategoryDTO;
import com.sotogito.model.dto.MenuDTO;

import java.sql.Connection;

import static com.sotogito.common.JDBCTemplate.*;

/**
 * ## Serice
 * 1. 비지니스 로직 처리와 트랜잭션 관리를 담당
 *      - 사용자의 요청에 따라 순차적으로 실행해야되는 작업들을 하나로 묶어 관리 - public(main) > private,private...
 *      - 중간과정에 문제 발생시 rollback 진행해야되므로 하나의 트핸잭션으로 묶어 관리
 *
 * 3. VIEW(UI) 와 MODEL(db)사이의 계층
 * 3. 처리 과정
 *      1) Connection 생성
 *      2) 순차적으로 작업 실행
 *      3) 트랜잭션 처리가 필요한 경우 트랜잭션 처리
 *      4) Connection 반납
 *
 * * 비지니스 로직 : 데이터베이스와 사용자 인터페이스(ui)같의 정보교환을 위한 규칙안 알고리즘 의미
 *
 *
 *
 */
public class MenuService {

    ///  만약 카테고리의 등록과 메뉴의 등록이 둘 다 성공이여야 진행될 수 있는 로직인 경우 같이 묶어 메서드로 만든다.
    /// 카테고리 등록과 메뉴 등록이 독립적이고 결과가 서의 영향에 미치지 않는 경우는 분리
    public int registCategoryAndMenu(CategoryDTO categoryDTO, MenuDTO menuDTO) {
        int result = 0;

        MenuDAO menuDAO = new MenuDAO();
        Connection conn = getConnection();

        int categoryInsertResult = menuDAO.insertCategory(conn, categoryDTO);
        int menuInsertResult = menuDAO.insertMenu(conn, menuDTO);

        if (categoryInsertResult == 1 && menuInsertResult == 1) {
            commit(conn);
            result = 1;
        } else {
            rollback(conn);
        }
        close(conn);
        return result;
    }

    public int registCategoryAndMenu2(CategoryDTO categoryDTO, MenuDTO menuDTO) {
        int result = 0;

        MenuDAO menuDAO = new MenuDAO();
        Connection conn = getConnection();

        /// 신규 카테고리 추가
        int categoryInsertResult = menuDAO.insertCategory(conn, categoryDTO);

        /// select로 신규 추가 카테고리의 카테고리 코드 알아오기
        int currentCategoryCode = menuDAO.selectCurrentCategoryCode(conn); //서비스에서 카테고리 코드를 Dao에서 알아옴

        /// 메뉴에 카테고리 코드 SET
        menuDTO.setCategoryCode(currentCategoryCode);

        /// 신규 메뉴 추가
       // int categoryInsertResult = menuDAO.insertCategory(conn, categoryDTO); //카테고리를 먼저 추가해야해야됨
        int menuInsertResult = menuDAO.insertMenu(conn, menuDTO);

        /// 결과 확인 및 결과 처리
        if (categoryInsertResult == 1 && menuInsertResult == 1) {
            commit(conn);
            result = 1;
        } else {
            rollback(conn);
        }
        close(conn);
        return result;
    }

}

