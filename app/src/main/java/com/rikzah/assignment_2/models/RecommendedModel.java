package com.rikzah.assignment_2.models;

public class RecommendedModel {
    String name;
    int price;
    String img_url;
    public RecommendedModel() {}
    public RecommendedModel(String name, int price, String img_url) {
        this.name = name;
        this.price = price;
        this.img_url = img_url;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    }}

