package com.seungjoo.model.dto;

public class MenuDTO {

    private int menuCode;
    private String menuName;
    private int menuPrice;
    private int categoryCode;
    private String orderableStatus;


    public MenuDTO() {}

    public MenuDTO(int menucode, String menuname, int menuPrice, int categoryCode, String orderableStatus) {
        this.menuCode = menucode;
        this.menuName = menuname;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "menucode=" + menuCode +
                ", menuname='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", categoryCode='" + categoryCode + '\'' +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }

    public void setMenucode(int menucode) {
        this.menuCode = menucode;
    }

    public String getMenuname() {
        return menuName;
    }

    public void setMenuname(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }
}

