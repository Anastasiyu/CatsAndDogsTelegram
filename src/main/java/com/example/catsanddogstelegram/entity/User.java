package com.example.catsanddogstelegram.entity;

import lombok.Data;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;


@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "user_time")
    private Timestamp userTime;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_age")
    private int userAge;

    @OneToMany(mappedBy = "user")
    private Collection<Animal> animals;

    @Override
    public String toString() {
        return "Пользователь" +
                "  " + chatId + " " + userAge +
                " "  + userName +
                "/ " + userTime;
    }
}
