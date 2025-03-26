package com.younggalee.menu.model.dto;

public class MenuDto {
    private String menuName;
    private int menuPrice;
    private String category;
    private String orderableStatus;
    private int menuCode;

    public MenuDto(String menuName, int menuPrice, String category, String orderableStatus, int menuCode) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.category = category;
        this.orderableStatus = orderableStatus;
        this.menuCode = menuCode;
    }

    public int getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }

    public MenuDto(){}

    public MenuDto(String menuName, int menuPrice, String category, String orderableStatus) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.category = category;
        this.orderableStatus = orderableStatus;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "MenuDto{" +
                "menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", category='" + category + '\'' +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}
