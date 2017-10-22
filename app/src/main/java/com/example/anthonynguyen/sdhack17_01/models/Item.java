package com.example.anthonynguyen.sdhack17_01.models;

/**
 * Created by anthonynguyen on 10/21/17.
 */

public class Item {
    public String picturePath;
    public int expirationDate;
    public int phone;
    public String address;

    public Item(String picturePath, int expirationDate, int phone, String address) {
        this.picturePath = picturePath;
        this.expirationDate = expirationDate;
        this.phone = phone;
        this.address = address;
    }

    public String getPicture() {return picturePath;}

    public int getExpirationDate() {return expirationDate;}

    public int getPhone() {return phone;}

    public String getAddress() {return address;}
}
