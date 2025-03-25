package com.podoseee.menu.model.service;

import com.podoseee.menu.model.dao.MenuDao;
import com.podoseee.menu.model.dto.CategoryDto;
import com.podoseee.menu.model.dto.MenuDto;

import java.sql.Connection;
import java.util.List;

import static com.podoseee.common.JDBCTemplate.*;

public class MenuService {

    private MenuDao menuDao = new MenuDao();

    public List<MenuDto> selectMenuList(){
        Connection conn = getConnection();
        List<MenuDto> list = menuDao.selectAllMenu(conn);

        return list;
    }

    public List<CategoryDto> selectCategoryList() {
        Connection conn = getConnection();
        List<CategoryDto> list = menuDao.selectAllCategory(conn);
        close(conn);
        return list;
    }

    public int registMenu(MenuDto menu){
        Connection conn = getConnection();
        int result = menuDao.insertMenu(conn, menu);
        if(result > 0){
            commit(conn);
        }else{
            rollback(conn);
        }
        close(conn);
        return result;
    }

    public int removeMenu(int menuCode){
        Connection conn = getConnection();
        int result = menuDao.deleteMenu(conn, menuCode);
        if(result > 0){
            commit(conn);
        }else{
            rollback(conn);
        }
        close(conn);
        return result;
    }


}
