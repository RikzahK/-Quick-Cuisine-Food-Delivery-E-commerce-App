package com.rikzah.assignment_2.models;

import java.io.Serializable;

public class OrderHistoryModel implements Serializable {
    String productName,productPrice,totalQuantity,orderaddress;
    int totalprice;

    String currentDate,currentTime,productImage, documentId;
    public OrderHistoryModel(String productName, String productPrice, String totalQuantity, String orderaddress, int totalprice, String currentDate, String currentTime, String productImage, String documentId) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.orderaddress = orderaddress;
        this.totalprice = totalprice;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.productImage = productImage;
        this.documentId = documentId;}
    public String getOrderaddress() {
        return orderaddress;
    }
    public void setOrderaddress(String orderaddress) {
        this.orderaddress = orderaddress;
    }
    public OrderHistoryModel() {}
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
    public String getTotalQuantity() {
        return totalQuantity;
    }
    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    public int getTotalprice() {
        return totalprice;
    }
    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }
    public String getCurrentDate() {
        return currentDate;
    }
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
    public String getCurrentTime() {
        return currentTime;
    }
    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
    public String getProductImage() {
        return productImage;
    }
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
