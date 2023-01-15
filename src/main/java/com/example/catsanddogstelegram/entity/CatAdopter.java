package com.example.catsanddogstelegram.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "cat_adopters")
public class CatAdopter {
    @Id
    private long chatId;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_age")
    private  int age;
    @Column(name = "user_time")
    private Timestamp registerTime;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String proneNumber;
    @Column(name = "address")
    private String address;
    @OneToOne
    @JoinColumn(name = "animal_id")
    private Cat cat;
}
