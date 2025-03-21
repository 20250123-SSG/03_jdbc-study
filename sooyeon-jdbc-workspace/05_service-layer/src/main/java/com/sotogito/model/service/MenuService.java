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
 * 2. 처리 과정
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

    public int registCategoryAndMenu(CategoryDTO categoryDTO, MenuDTO menuDTO) {
        int result = 0;

        MenuDAO menuDAO = new MenuDAO();
        Connection conn = getConnection();

        int categoryInsertResult = menuDAO.insertCategory(conn, categoryDTO);
        int menuInsertResult = menuDAO.insertMenu(conn, menuDTO);

        if(categoryInsertResult == 1 && menuInsertResult == 1){
            commit(conn);
            result = 1;
        }else {
            rollback(conn);
        }
        close(conn);
        return result;
    }
}

