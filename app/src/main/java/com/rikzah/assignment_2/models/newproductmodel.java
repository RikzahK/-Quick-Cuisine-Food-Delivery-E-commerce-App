package com.rikzah.assignment_2.models;

import java.io.Serializable;

public class newproductmodel implements Serializable {
    String Restautrant,type,name,description;
    int price;
    String img_url,currentDate;

    public newproductmodel() {}
    public String getRestautrant() {
        return Restautrant;
    }
    public void setRestautrant(String restautrant) {
        Restautrant = restautrant;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPrice() {
        return String.valueOf(price);
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getImg_url() {
        return img_url;
    }
    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
    public String getCurrentDate() {
        return currentDate;
    }
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
    public newproductmodel(String restautrant, String type, String name, String description, int price, String img_url, String currentDate) {
        Restautrant = restautrant;
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.img_url = img_url;
        this.currentDate = currentDate;}}
