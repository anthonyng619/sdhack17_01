package com.example.anthonynguyen.sdhack17_01.models;

/**
 * Created by anthonynguyen on 10/21/17.
 */

public class Item {
    public String imgTitle;
    public String imgUrl;
    public int expirationDate;
    public int phone;
    public String address;

    public Item(String imgTitle, String imgUrl, int expirationDate, int phone, String address) {
        this.imgTitle = imgTitle;
        this.imgUrl = imgUrl;
        this.expirationDate = expirationDate;
        this.phone = phone;
        this.address = address;
    }

    public String getImgUrl() {return imgUrl;}

    public int getExpirationDate() {return expirationDate;}

    public int getPhone() {return phone;}

    public String getAddress() {return address;}
}
