package com.seungjoo.menu.model.service;

import com.seungjoo.menu.model.dao.MenuDao;
import com.seungjoo.menu.model.dto.MenuDto;

import java.sql.Connection;
import java.util.List;

import static com.seungjoo.common.JDBCTemplate.*;

public class MenuService {
    private MenuDao menuDao = new MenuDao();

    public List<MenuDto> selectMenuList(){
        Connection conn = getConnection();
        List<MenuDto> list = menuDao.selectAllMenu(conn);
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
}
