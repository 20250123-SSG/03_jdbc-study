package com.podoseee.menu.controller;

import com.podoseee.menu.model.dto.CategoryDto;
import com.podoseee.menu.model.service.CategoryService;
import com.podoseee.menu.view.PrintResultView;

import java.util.List;
import java.util.Map;

public class CategoryController {

    private CategoryService categoryService = new CategoryService();
    private PrintResultView printResultView = new PrintResultView();

    public List<CategoryDto> selectCategoryList(){
        List<CategoryDto> list = categoryService.selectCategoryList();
        return list;
    }

    public void registCategory(Map<String, String> requestParam){

        CategoryDto category = new CategoryDto();
        category.setCategoryCode( Integer.parseInt(requestParam.get("code")));
        category.setCategoryName( requestParam.get("name"));
        category.setRefCategoryCode( Integer.parseInt(requestParam.get("refcode")));

        int result = categoryService.registCategory(category);

        if(result > 0){
            printResultView.displaySuccessMessage("insert");
        }else{
            printResultView.displayFailMessage("insert");
        }
    }
}
