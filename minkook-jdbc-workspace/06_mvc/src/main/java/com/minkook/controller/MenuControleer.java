package com.minkook.controller;

import com.minkook.model.dto.MenuDto;
import com.minkook.model.service.MenuSerivce;
import com.minkook.view.PrintResultView;

import java.util.List;

/*
    ## Controller ##
    1. 사용자 UI를 통해 요청을 보내면 해당 요청을 처리하는 역할
    2. 처리과정
        1) View에서 사용자가 입력 한 값을 파라미터로 전달받아 검증 및 가공
        2) 다수의 데이터를 전송해야될 경우 DTO와 같은 특정 인스턴스에 데이터에 담기
        3) 해당 요청에 필요한 비즈니스 로직의 Service 측 메소드 호출(데이터 전달)
        4) 비즈니스 로직 처리 결과를 반환 받아 어떤 응답을 할 껀지 결정
            -응답 데이터만 돌려줄건지 (후에 REST방식의 개념)
            -응답 화면을 출력시킬건지 (
 */
public class MenuControleer {
    private MenuSerivce menuSerivce = new MenuSerivce();
    private PrintResultView printResultView = new PrintResultView();

    public List<MenuDto> selectMenuList(){
        List<MenuDto> list = menuSerivce.selectMenuList();
        return list;
    }
}
