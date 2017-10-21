package com.example.anthonynguyen.sdhack17_01.models;

/**
 * Created by Keng on 10/21/2017.
 */

public class User {
    public String username;
    public String usernameId;
    public String email;

    public User() {

    }
    public User(String username, String usernameId, String email) {
        this.username = username;
        this.usernameId = usernameId;
        this.email = email;
    }
}
