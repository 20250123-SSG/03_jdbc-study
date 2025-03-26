package com.ino.menu.controller;

import com.ino.menu.model.dto.CategoryDto;
import com.ino.menu.model.dto.MenuDto;
import com.ino.menu.model.service.MenuService;
import com.ino.menu.view.PrintResultView;

import java.util.List;
import java.util.Map;

public class MenuController {

    private MenuService menuService = new MenuService();
    private PrintResultView printResultView = new PrintResultView();

    public List<MenuDto> selectMenuList(){
        List<MenuDto> list = menuService.selectMenuList();
        return list;
    }

    public void insertMenu(Map<String, String> menu) {
        MenuDto menuDto = new MenuDto();
        menuDto.setMenuName(menu.get("name"));
        menuDto.setMenuPrice((Integer.parseInt(menu.get("price"))));
        menuDto.setCategory(menu.get("category"));
        menuDto.setOrderableStatus(menu.get("orderable"));
        if (menuService.insertMenu(menuDto) > 0) {
            PrintResultView.displaySuccessMessage("insert");
        } else {
            PrintResultView.displayFailureMessage("insert");
        }
    }

    public List<CategoryDto> getAllCategory() {
        return menuService.getAllCategory();
    }

    public void updateMenu(int menuCode, Map<String, String> requestParam) {
        MenuDto menuDto = new MenuDto();
        menuDto.setMenuName(requestParam.get("name"));
        menuDto.setMenuPrice((Integer.parseInt(requestParam.get("price"))));
        menuDto.setCategory(requestParam.get("category"));
        menuDto.setOrderableStatus(requestParam.get("orderable"));
        if (menuService.updateMenu(menuCode, menuDto) > 0) {
            PrintResultView.displaySuccessMessage("update");
        } else {
            PrintResultView.displayFailureMessage("update");
        }
    }
}
