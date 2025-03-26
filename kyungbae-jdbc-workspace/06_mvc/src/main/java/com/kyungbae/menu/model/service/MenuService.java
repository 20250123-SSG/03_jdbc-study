package com.kyungbae.menu.model.service;

import com.kyungbae.menu.model.dao.MenuDao;
import com.kyungbae.menu.model.dto.CategoryDto;
import com.kyungbae.menu.model.dto.MenuDto;

import java.sql.Connection;
import java.util.List;

import static com.kyungbae.common.JDBCTemplate.*;

public class MenuService {

    private MenuDao menuDao = new MenuDao();

    public List<MenuDto> selectMenuList() {
        Connection conn = getConnection();
        List<MenuDto> list = menuDao.selectAllMenu(conn);
        close(conn);
        return list;
    }

    public int registMenu(MenuDto menu) {
        Connection conn = getConnection();
        int result = menuDao.insertMenu(conn, menu);
        if (result > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        close(conn);
        return result;
    }

    public List<CategoryDto> selectCategoryList() {
        Connection conn = getConnection();
        List<CategoryDto> categoryList = menuDao.selectAllCategory(conn);
        close(conn);
        return categoryList;
    }

    public MenuDto selectMenu(int menuCode) {
        Connection conn = getConnection();
        MenuDto menu = menuDao.selectMenuByCode(conn, menuCode);
        close(conn);
        return menu;
    }

    public int modifyMenu(MenuDto modifyMenuInfo) {
        Connection conn = getConnection();
        int result = menuDao.updateMenu(conn, modifyMenuInfo);
        if (result > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        close(conn);
        return result;
    }

    public int deleteMenu(int menuCode){
        Connection conn = getConnection();
        int result = menuDao.deleteMenu(conn, menuCode);
        if (result > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        close(conn);
        return result;
    }
}
