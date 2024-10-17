package com.rikzah.assignment_2.models;

import java.io.Serializable;
public class MycartModel implements Serializable {

    String productName, productPrice, totalQuantity;
    int totalprice;

    String currentDate, currentTime,productImage,documentId;

    public MycartModel() {}
    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    public MycartModel(String productName, String productPrice, String totalQuantity, int totalprice, String currentDate, String currentTime, String productImage, String documentId) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.totalprice = totalprice;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.productImage = productImage;
        this.documentId = documentId;}

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
}