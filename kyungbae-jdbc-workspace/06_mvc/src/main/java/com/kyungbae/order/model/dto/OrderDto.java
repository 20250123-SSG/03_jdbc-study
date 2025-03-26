package com.kyungbae.order.model.dto;

import java.util.List;

public class OrderDto {

    private int orderCode;
    private String orderDate;
    private String orderTime;
    private int totalOrderPrice;

    // 한 주문에 여러 주문메뉴가 있음
    private List<OrderMenuDto> orderMenuList;

    public OrderDto (){}

    public OrderDto(int orderCode, String orderDate, String orderTime, int totalOrderPrice) {
        this.orderCode = orderCode;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.totalOrderPrice = totalOrderPrice;
    }

    public OrderDto(int orderCode, String orderDate, String orderTime, int totalOrderPrice, List<OrderMenuDto> orderMenuList) {
        this.orderCode = orderCode;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.totalOrderPrice = totalOrderPrice;
        this.orderMenuList = orderMenuList;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(int totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public List<OrderMenuDto> getOrderMenuList() {
        return orderMenuList;
    }

    public void setOrderMenuList(List<OrderMenuDto> orderMenuList) {
        this.orderMenuList = orderMenuList;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderCode=" + orderCode +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", totalOrderPrice=" + totalOrderPrice +
                '}';
    }
}
