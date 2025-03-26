package com.inyong.model.service;

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

import com.inyong.model.dao.MenuDAO;
import com.inyong.model.dto.CategoryDTO;
import com.inyong.model.dto.MenuDTO;

import java.sql.Connection;

import static com.inyong.common.JDBCTemplate.*;

/*
    ## Service ##
    1. 비즈니스 로직처리와 트랜잭션를 처리 담당
       - 사용자의 요청에 따라 순차적으로 진행시킬 작업을 하나로 묶어 관리
    2. 사용자인터페이스(UI)와 데이터베이스(Model) 사이의 계층
    3. 처리 과정
       1) Connection 생성
       2) 작업을 순차적으로 진행
       3) 트랜잭션 처리가 필요하다면 트랜잭션 처리
       4) Connection 반납
 */
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

    public int registCategoryAndMenu2(CategoryDTO category, MenuDTO menu){
        // 신규 카테고리 등록 후 등록시 생성된 카테고리번호로 메뉴 등록

        int result = 0; // 해당 작업들 전체의 성공여부를 판별할 변수 (최종결과)
        MenuDAO menuDao = new MenuDAO();

        Connection conn = getConnection();

        // 1) 신규 카테고리 등록
        int result1 = menuDao.insertCategory(conn, category);
        // 2) 1번과정으로 등록된 카테고리번호를 조회
        int currCategoryCode = menuDao.selectCurrentCategoryCode(conn);
        menu.setCategoryCode(currCategoryCode);
        // 3) 신규 메뉴 등록
        int result2 = menuDao.insertMenu(conn, menu);

        if(result1 > 0 && result2 > 0){
            commit(conn);
            result = 1;
        }else{
            rollback(conn);
        }

        return result;

    }









}
