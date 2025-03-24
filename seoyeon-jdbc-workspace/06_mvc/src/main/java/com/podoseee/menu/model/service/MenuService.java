package com.podoseee.menu.model.service;

import com.podoseee.menu.model.dao.MenuDao;
import com.podoseee.menu.model.dto.MenuDto;

import java.sql.Connection;
import java.util.List;

import static com.podoseee.common.JDBCTemplate.getConnection;

public class MenuService {

    private MenuDao menuDao = new MenuDao();

    public List<MenuDto> selectMenuList(){
        Connection conn = getConnection();
        List<MenuDto> list = menuDao.selectAllMenu(conn);

        return list;
    }

}
