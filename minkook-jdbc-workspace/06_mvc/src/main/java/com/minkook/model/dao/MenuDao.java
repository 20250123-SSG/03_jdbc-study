package com.minkook.model.dao;


import com.minkook.model.dto.MenuDto;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.minkook.common.JDBCTemplate.close;


public class MenuDao {
    private Properties prop = new Properties();

    public MenuDao(){

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/minkook/config/connection-config.properties"));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public List<MenuDto> selectAllMenu(Connection conn){
        //select문 -> 여러행 조회
        List<MenuDto> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("selectAllMenu");

        try {
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();

            while (rset.next()){
                list.add(new MenuDto(
                            rset.getInt("menu_code")
                           ,rset.getString("menu_name")
                           ,rset.getInt("menu_price")
                           ,rset.getString("category_name")
                           ,rset.getString("orderable_status")
                ));
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }finally {
            close(rset);
            close(pstmt);
        }

        return list;

    }
}
