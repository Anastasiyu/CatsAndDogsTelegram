package com.example.catsanddogstelegram.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "volunteers")
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "chat_id")
    private long chatId;
    @Column(name = "name")
    private String name;
    @Column(name = "is_online")
    private boolean isOnline;
}
