package com.example.litepal.application;

import com.example.litepal.model.User;

import org.litepal.LitePalApplication;


public class MyApplication extends LitePalApplication {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
