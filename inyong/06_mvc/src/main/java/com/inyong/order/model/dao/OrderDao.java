package com.inyong.order.model.dao;

import com.inyong.menu.model.dto.MenuDto;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.inyong.common.JDBCTemplate.close;

public class OrderDao {

    private Properties prop = new Properties();

    public OrderDao(){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/kangbroo/mapper/order-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MenuDto> selectMenuByCategory(Connection conn, int categoryCode){

        // select문 => 여러행 => ResultSet => List<MenuDto>
        List<MenuDto> list = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("selectMenuByCategory");

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryCode);

            rset = pstmt.executeQuery();

            while(rset.next()){
                MenuDto menu = new MenuDto();
                menu.setMenuCode( rset.getInt("menu_code") );
                menu.setMenuName( rset.getString("menu_name") );
                menu.setMenuPrice( rset.getInt("menu_price") );

                list.add(menu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            close(rset);
            close(pstmt);
        }

        return list;
    }




}
