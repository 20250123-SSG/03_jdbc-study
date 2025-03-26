package com.younggalee.menu.model.service;

import com.younggalee.menu.model.dao.MenuDao;
import com.younggalee.menu.model.dto.CategoryDto;
import com.younggalee.menu.model.dto.MenuDto;

import java.sql.Connection;
import java.util.List;

import static com.younggalee.common.JDBCTemplate.*;

public class MenuService {
    private MenuDao menuDao = new MenuDao();

    public List<MenuDto> selectMenuList(){
        Connection conn = getConnection();
        List<MenuDto> list = menuDao.selectAllMenu(conn);
        close(conn);
        return list;
    }

    public List<CategoryDto> selectCategoryList(){
        Connection conn = getConnection();
        List<CategoryDto> list = menuDao.selectAllCategory(conn);
        close(conn);
        return list;
    }

    public int registMenu(MenuDto menu){
        Connection conn = getConnection();
        int result = menuDao.insertMenu(conn, menu);
        if (result > 0){
            commit(conn);
        } else {
            rollback(conn);
        }
        return result;
    }




}
