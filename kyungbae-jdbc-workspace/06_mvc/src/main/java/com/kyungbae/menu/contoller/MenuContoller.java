package com.kyungbae.menu.contoller;

import com.kyungbae.menu.model.dto.CategoryDto;
import com.kyungbae.menu.model.dto.MenuDto;
import com.kyungbae.menu.model.service.MenuService;
import com.kyungbae.menu.view.PrintResultView;

import java.util.List;
import java.util.Map;

public class MenuContoller {

    private MenuService ms = new MenuService();
    private PrintResultView printResultView = new PrintResultView();


    public List<MenuDto> selectMenuList() {
        return ms.selectMenuList();
    }

    public void registMenu(Map<String, String> requestParam) {
        // 요청시 전달값을 [가공처리한 후] DTO에 담기
        MenuDto menu = new MenuDto();
        menu.setMenuName(requestParam.get("name"));
        menu.setMenuPrice(Integer.parseInt(requestParam.get("price")));
        menu.setCategory(requestParam.get("categoryCode"));
        menu.setOrderableStatus(requestParam.get("orderable").toUpperCase());

        int result = ms.registMenu(menu);
        // 응답화면 지정 출력
        if (result > 0) { // 등록 성공
            printResultView.displaySuccessMessage("insert");
        } else { // 등록 실패
            printResultView.displayFailMessage("insert");
        }
    }

    public List<CategoryDto> selectCategoryList() {
        return ms.selectCategoryList();
    }

    public MenuDto selectMenu(String menuCode) {
        MenuDto menu = ms.selectMenu(Integer.parseInt(menuCode));
        return menu;
    }

    public void modifyMenu(Map<String, String> requestParam, MenuDto originMenu) {
        String menuName = requestParam.get("menuName");
        int menuPrice = Integer.parseInt(requestParam.get("menuPrice"));
        String categoryCode = requestParam.get("categoryCode");
        String orderable = requestParam.get("orderable");
        MenuDto modifyMenuInfo = new MenuDto(
                Integer.parseInt(requestParam.get("menuCode")),
                menuName.equals("0") ? originMenu.getMenuName() : menuName,
                menuPrice == 0 ? originMenu.getMenuPrice() : menuPrice,
                categoryCode.equals("0") ? originMenu.getCategory() : categoryCode,
                orderable.equals("0") ? originMenu.getOrderableStatus() : orderable
        );

        int result = ms.modifyMenu(modifyMenuInfo);
        if (result > 0) {
            printResultView.displaySuccessMessage("update");
        } else {
            printResultView.displayFailMessage("update");
        }
    }
}
