package com.seungjoo.model.service;



/*
    ## Service ##
    1. 비즈니스 로직 처리와 트랜잭션 관리를 담당
       - 사용자의 요청에 따라 순차적으로 실행해야되는 작업들을 하나로 묶어 관리
       - 중간과정에 문제 발생시 rollback 진행해야되므로 하나의 트랜잭션으로 묶어 관리
    2. 처리 과정
       1) Connection 생성
       2) 순차적으로 작업 실행
       3) 트랜잭션 처리가 필요할 경우 트랜잭션 처리
       4) Connection 반납

    * 비즈니스 로직 : 데이터베이스와 사용자인터페이스(UI)간의 정보 교환을 위한 규칙이나 알고리즘 의미
 */

import com.seungjoo.model.dao.MenuDAO;
import com.seungjoo.model.dto.CategoryDTO;
import com.seungjoo.model.dto.MenuDTO;

import java.sql.Connection;

import static com.seungjoo.common.JDBCTemplate.*;

public class MenuService {

    // 신규 카테고리와 메뉴 동시 추가
    public int registCategoryAndMenu(CategoryDTO category, MenuDTO menu){
        int result = 0; // 현재 비즈니스 로직의 최종 결과 담을 변수 (모든 작업이 성공되면 1, 아니면 0)

        MenuDAO dao = new MenuDAO();

        Connection conn = getConnection();

        int result1 = dao.insertCategory(conn, category);
        int result2 = dao.insertMenu(conn, menu);

        if(result1 > 0 && result2 > 0){
            commit(conn);
            result = 1;
        }else{
            rollback(conn);
        }

        close(conn);

        return result;
    }

}