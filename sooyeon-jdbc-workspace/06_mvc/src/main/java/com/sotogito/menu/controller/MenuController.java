package com.sotogito.menu.controller;

import com.sotogito.menu.MenuFunction;
import com.sotogito.menu.model.dto.CategoryDto;
import com.sotogito.menu.model.dto.MenuDto;
import com.sotogito.menu.model.service.MenuService;
import com.sotogito.menu.view.PrintResultView;


import java.util.List;
import java.util.Map;

/**
 * ## Controller
 * 1. 사용자 ui를 통해서 요청을 보내면 해당 요청을 처리하는 역할
 * 2. 처리 과정
 * 1) View에서 사용자가 입력한 값을 파라미터로 전달받아 검증 및 가공
 * 2) 다수의 데이터를 전송해야될 경우 Dto와 같은 특정 인스턴스 객체에 데이터 담기
 * 3) 해당 요청에 필요한 비지니스 로직의 Serivce측 메서드 호출 (데이터 전달)
 * 4) 비지니스 로직 처리 결과를 반환 받아 어떤 응답을 할 건지 결정
 * - 응답 데이터만 전달(REST 방식의 개념) vs 응답 화면(PrintResultView)을 출력
 */
public class MenuController {
    private MenuService menuService = new MenuService();
    private PrintResultView printResultView = new PrintResultView();


    public List<MenuDto> selectMenuList() {
        return menuService.selectMenuList(); //결과를 반환? 아니면 view로? 는 컨트롤러 맘
    }

    public List<CategoryDto> selectCategoryList() {
        return menuService.selectCategoryList();
    }

    public void registMenu(Map<String, String> requestParam) {
        /// Map의 의존관계를 하위까지 내려갈 경우 안조음 - 컨트롤러에서 끊어내야함
        ///  데이터를 받아 검증 및 가공 -> DTO로 담기

        MenuDto menuDto = new MenuDto();
        menuDto.setMenuName(requestParam.get("menuName"));
        menuDto.setMenuPrice(Integer.parseInt(requestParam.get("menuPrice"))); /// 정수로 추가 가공
        menuDto.setCategory(requestParam.get("categoryNum"));
        menuDto.setOrderableStatus(requestParam.get("orderable").toUpperCase()); /// 대문자로 추가 가공

        int insertResult = menuService.registMenu(menuDto);

        /// 응답화면을 지정해서 출력
        if (insertResult == 1) {
            printResultView.displaySuccessMessage(MenuFunction.INSERT);
        }else {
            printResultView.displayFailMessage(MenuFunction.INSERT);
        }
    }

    public void updateMenu(Map<String, String> requestParam) {
        MenuDto newMenu = new MenuDto(
                Integer.parseInt(requestParam.get("updateMenuCode"))
                ,requestParam.get("menuName")
                ,Integer.parseInt(requestParam.get("menuPrice"))
                ,requestParam.get("categoryNum")
                ,requestParam.get("orderable").toUpperCase()
        );

        int updateResult = menuService.updateMenu(newMenu);

        if(updateResult == 1){
            printResultView.displaySuccessMessage(MenuFunction.UPDATE);
        }else {
            printResultView.displayFailMessage(MenuFunction.UPDATE);
        }
    }

    public void deleteMenu(Map<String, String> requestParam){
        int removeMenuCode = Integer.parseInt(requestParam.get("removeMenuCode"));

        int deleteResult = menuService.deleteMenu(removeMenuCode);

        if(deleteResult == 1){
            printResultView.displaySuccessMessage(MenuFunction.DELETE);
        }else {
            printResultView.displayFailMessage(MenuFunction.DELETE);
        }
    }

}
