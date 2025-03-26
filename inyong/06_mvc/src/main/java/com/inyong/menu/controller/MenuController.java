package com.inyong.menu.controller;

import com.inyong.menu.model.dto.CategoryDto;
import com.inyong.menu.model.dto.MenuDto;
import com.inyong.menu.model.service.MenuService;
import com.inyong.menu.view.PrintResultView;

import java.util.List;
import java.util.Map;

/*
    ## Controller ##
    1. 사용자UI를 통해 요청을 보내면 해당 요청을 처리하는 역할
    2. 처리과정
       1) View에서 사용자가 입력한 값을 파라미터로 전달받아 검증 및 가공
       2) 다수의 데이터를 전송해야될 경우 DTO와 같은 특정 인스턴스에 데이터에 담기
       3) 해당 요청에 필요한 비즈니스 로직의 Service측 메소드 호출 (데이터 전달)
       4) 비즈니스 로직 처리 결과를 반환 받아 어떤 응답을 할 껀지 결정
          - 응답 데이터만 돌려줄껀지 (후에 REST방식의 개념)
          - 응답 화면(PrintResultView)을 출력시킬껀지
 */
public class MenuController {

    private MenuService menuService = new MenuService();
    private PrintResultView printResultView = new PrintResultView();

    public List<MenuDto> selectMenuList(){
        List<MenuDto> list = menuService.selectMenuList();
        return list;
    }


    public void registMenu(Map<String, String> requestParam){

        // 요청시 전달값을 [가공처리한 후] DTO 담기
        MenuDto menu = new MenuDto();
        menu.setMenuName( requestParam.get("name") );
        menu.setMenuPrice( Integer.parseInt(requestParam.get("price")) );
        menu.setCategory( requestParam.get("category") );
        menu.setOrderableStatus( requestParam.get("orderable").toUpperCase() );

        int result = menuService.registMenu(menu);

        // 응답 화면(메세지 출력)을 지정해서 출력
        if(result > 0){ // 성공
            printResultView.displaySuccessMessage("insert");
        }else{ // 실패
            printResultView.displayFailMessage("insert");
        }

    }

    public List<CategoryDto> selectCategoryList(){
        List<CategoryDto> list = menuService.selectCategoryList();
        return list;
    }

    public void modifyMenu(Map<String, String> requestParam){

        MenuDto menu = new MenuDto();
        menu.setMenuCode( Integer.parseInt(requestParam.get("code")) );
        menu.setMenuName( requestParam.get("name") );
        menu.setMenuPrice( Integer.parseInt(requestParam.get("price")) );
        menu.setCategory( requestParam.get("category") );
        menu.setOrderableStatus( requestParam.get("orderable") );

        int result = menuService.modifyMenu(menu);

        if(result > 0){
            printResultView.displaySuccessMessage("update");
        }else{
            printResultView.displayFailMessage("update");
        }

    }

    public void removeMenu(Map<String, String> requestParam){
        int menuCode = Integer.parseInt(requestParam.get("code"));

        int result = menuService.removeMenu(menuCode);

        if(result > 0){
            printResultView.displaySuccessMessage("delete");
        }else{
            printResultView.displayFailMessage("delete");
        }

    }


}
