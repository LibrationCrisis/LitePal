package com.example.litepal.application;

import com.example.litepal.model.User;

import org.litepal.LitePalApplication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyApplication extends LitePalApplication {
    private User user;

}
