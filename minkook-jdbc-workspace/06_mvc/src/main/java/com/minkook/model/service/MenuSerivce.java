package com.minkook.model.service;

import com.minkook.model.dao.MenuDao;
import com.minkook.model.dto.MenuDto;

import java.sql.Connection;
import java.util.List;

import static com.minkook.common.JDBCTemplate.getConnection;

public class MenuSerivce {
    private MenuDao menuDao = new MenuDao();

    public List<MenuDto> selectMenuList(){
        Connection conn = getConnection();
        List<MenuDto> list = menuDao.selectAllMenu(conn);
        return list;
    }


}
