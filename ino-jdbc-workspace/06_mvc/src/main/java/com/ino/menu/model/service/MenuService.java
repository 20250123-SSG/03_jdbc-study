package com.ino.menu.model.service;

import com.ino.menu.model.dao.MenuDao;
import com.ino.menu.model.dto.CategoryDto;
import com.ino.menu.model.dto.MenuDto;

import java.sql.Connection;
import java.util.List;

import static com.ino.common.JDBCTemplate.*;

public class MenuService {
    MenuDao menuDao = new MenuDao();

    public List<MenuDto> selectMenuList() {
        Connection conn = getConnection();
        List<MenuDto> list = menuDao.selectAllMenu(conn);
        close(conn);
        return list;
    }

    public int insertMenu(MenuDto menu) {
        Connection conn = getConnection();
        int result = menuDao.insertMenu(menu, conn);
        if (result > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        close(conn);
        return result;
    }

    public List<CategoryDto> getAllCategory() {
        Connection conn = getConnection();
        List<CategoryDto> list = menuDao.getAllCategory(conn);
        close(conn);
        return list;
    }

    public int updateMenu(int menuCode, MenuDto requestParam) {
        Connection conn = getConnection();
        int result = menuDao.updateMenu(conn, menuCode, requestParam);
        if (result > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        close(conn);
        return result;
    }
}
