package com.podoseee.order.model.dto;

import com.podoseee.menu.model.dto.MenuDto;

public class OrderMenuDto {

    private int orderCode;
    private int menuCode;
    private int orderAmount;

    /*
    private String menuName;
    private int menuPrice;
    private String category;
    */
    // has-a 관계
    private MenuDto menu;

    public MenuDto getMenu() {
        return menu;
    }

    public void setMenu(MenuDto menu) {
        this.menu = menu;
    }

    public OrderMenuDto(){}

    public OrderMenuDto(int orderCode, int menuCode, int orderAmount) {
        this.orderCode = orderCode;
        this.menuCode = menuCode;
        this.orderAmount = orderAmount;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public int getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Override
    public String toString() {
        return "OrderMenuDto{" +
                "orderCode=" + orderCode +
                ", menuCode=" + menuCode +
                ", orderAmount=" + orderAmount +
                '}';
    }

}
