package com.seungjoo.menu.controller;

import com.seungjoo.menu.model.dto.MenuDto;
import com.seungjoo.menu.model.service.MenuService;
import com.seungjoo.menu.view.PrintResultView;

import java.util.List;
import java.util.Map;
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
}
