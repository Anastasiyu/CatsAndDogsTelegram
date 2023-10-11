package com.example.catsanddogstelegram.record;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CatAdopterRecord {
    private long chatId;
    private String name;
    private  int age;
    private Timestamp registerTime;
    private String email;
    private String proneNumber;
    private String address;
    private int catId;
}
