package com.example.anthonynguyen.sdhack17_01.models;

/**
 * Created by anthonynguyen on 10/21/17.
 */

public class Item {
    public String imgTitle;
    public String imgUrl;
    public String expirationDate;
    public String phone;
    public String address;
    public String quantity;

    public Item() {}

    public Item(String imgTitle, String imgUrl, String expirationDate, String phone, String address, String quantity) {
        this.imgTitle = imgTitle;
        this.imgUrl = imgUrl;
        this.expirationDate = expirationDate;
        this.phone = phone;
        this.address = address;
        this.quantity = quantity;
    }

    public String getImgTitle() {return imgTitle;}

    public String getImgUrl() {return imgUrl;}

    public String getExpirationDate() {return expirationDate;}

    public String getPhone() {return phone;}

    public String getAddress() {return address;}

    public String getQuantity() {return quantity;}
}
