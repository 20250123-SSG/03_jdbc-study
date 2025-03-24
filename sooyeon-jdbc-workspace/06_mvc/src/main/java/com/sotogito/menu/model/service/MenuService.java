package com.sotogito.menu.model.service;

import com.sotogito.menu.model.dao.MenuDao;
import com.sotogito.menu.model.dto.CategoryDto;
import com.sotogito.menu.model.dto.MenuDto;

import java.sql.Connection;
import java.util.List;

import static com.sotogito.common.JDBCTemplate.*;

/**
 * Connection 객체 생성하고 close까지
 */
public class MenuService {
    private MenuDao menuDao = new MenuDao();


    public List<MenuDto> selectMenuList() {
        Connection conn = getConnection();

        List<MenuDto> menuList = menuDao.selectAllMenu(conn);
        close(conn);

        if (menuList == null || menuList.isEmpty()) {
            throw new IllegalArgumentException("비어잇삼");
        }
        return menuList;
    }

    public List<CategoryDto> selectCategoryList() {
        Connection conn = getConnection();

        List<CategoryDto> categoryList = menuDao.selectAllCategory(conn);
        close(conn);

        if (categoryList == null || categoryList.isEmpty()) {
            throw new IllegalArgumentException("업슴");
        }
        return categoryList;
    }

    public int registMenu(MenuDto menuDto) {
        Connection conn = getConnection();

        int insertResult = menuDao.registMenu(conn, menuDto);
        if (insertResult == 1) {
            commit(conn);
        } else {
            rollback(conn); //사실insert가 하나라 롤백이 필요없지만 다중 INSERT할때는 필요함
        }
        close(conn);

        return insertResult;
    }

    public int updateMenu(MenuDto menuDto) {
        Connection conn = getConnection();

        int updateResult = menuDao.updateMenu(conn, menuDto);
        if (updateResult == 1) {
            commit(conn);
        } else {
            rollback(conn);
        }
        close(conn);

        return updateResult;
    }

    public int deleteMenu(int menuCode) {
        Connection conn = getConnection();

        int deleteResult = menuDao.deleteMenu(conn, menuCode);

        if (deleteResult == 1) {
            commit(conn);
        } else {
            rollback(conn);
        }
        close(conn);

        return deleteResult;
    }

}
