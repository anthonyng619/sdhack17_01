package com.example.anthonynguyen.sdhack17_01.models;

/**
 * Created by anthonynguyen on 10/21/17.
 */

public class Item {
    public String imgTitle;
    public String imgUrl;
    public String expirationDate;
    public int phone;
    public String address;
    public int quantity;

    public Item() {}

    public Item(String imgTitle, String imgUrl, String expirationDate, int phone, String address, int quantity) {
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

    public int getPhone() {return phone;}

    public String getAddress() {return address;}

    public int getQuantity() {return quantity;}
}
