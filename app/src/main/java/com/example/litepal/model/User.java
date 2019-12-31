package com.example.litepal.model;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NonNull
public class User extends LitePalSupport implements Serializable {
    private Integer id;
    private String username;
    private String password;

}
