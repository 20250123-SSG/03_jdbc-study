package com.podoseee.menu.model.service;

import com.podoseee.menu.model.dao.CategoryDao;
import com.podoseee.menu.model.dto.CategoryDto;

import java.sql.Connection;
import java.util.List;

import static com.podoseee.common.JDBCTemplate.*;

public class CategoryService {

    private CategoryDao categoryDao = new CategoryDao();

    public List<CategoryDto> selectCategoryList() {
        Connection conn = getConnection();
        List<CategoryDto> list = categoryDao.selectAllCategory(conn);

        return list;
    }

    public int registCategory(CategoryDto category) {
        Connection conn = getConnection();
        int result = categoryDao.insertCategory(conn, category);
        if(result > 0){
            commit(conn);
        }else{
            rollback(conn);
        }
        close(conn);
        return result;
    }
}
