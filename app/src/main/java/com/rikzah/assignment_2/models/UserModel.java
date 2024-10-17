package com.rikzah.assignment_2.models;

import java.io.Serializable;

public class UserModel implements Serializable {
    String name, email, password, phone;
    //String addresscity;
    String addressdetails, cardname, cardnumber, securitycode, expirydate;

    public UserModel() {
    }

    public UserModel(String name, String email, String password, String phone, String addressdetails, String cardname, String cardnumber, String securitycode, String expirydate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.addressdetails = addressdetails;
        this.cardname = cardname;
        this.cardnumber = cardnumber;
        this.securitycode = securitycode;
        this.expirydate = expirydate;
    }

    public String getAddressdetails() {
        return addressdetails;
    }

    public void setAddressdetails(String addressdetails) {
        this.addressdetails = addressdetails;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getSecuritycode() {
        return securitycode;
    }

    public void setSecuritycode(String securitycode) {
        this.securitycode = securitycode;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
