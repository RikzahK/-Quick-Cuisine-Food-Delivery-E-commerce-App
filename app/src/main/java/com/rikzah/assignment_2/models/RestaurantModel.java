package com.rikzah.assignment_2.models;

import java.io.Serializable;

public class RestaurantModel implements Serializable {
    String name;
    String description;
    String rating;
    String delivery_time;
    int delivery_fee;
    String img_url;
    String discount;

    public RestaurantModel() {
    }

    public RestaurantModel(String name, String description, String rating, String delivery_time, int delivery_fee, String img_url, String discount) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.delivery_time = delivery_time;
        this.delivery_fee = delivery_fee;
        this.img_url = img_url;
        this.discount = discount;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getDelivery_fee() {
        return String.valueOf(delivery_fee);
    }

    public void setDelivery_fee(int delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
