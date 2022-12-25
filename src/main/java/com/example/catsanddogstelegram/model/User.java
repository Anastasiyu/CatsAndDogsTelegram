package com.example.catsanddogstelegram.model;

import org.hibernate.annotations.Entity;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.sql.Timestamp;
import java.util.Collection;


@Entity
@Table(name = "Users")
public class User {

    @Id
    private Long chatId;

    private Timestamp userTime; // переменная время регистрации
    private String userName;

    private int userAge;


    @OneToMany(mappedBy = "Users")
    private Collection<Dog> dogs;
    public Collection<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(Collection<Dog> students) {
        this.dogs = dogs;
    }

    public int getAge() {
        return userAge;
    }

    public void setAge(int age) {
        this.userAge = age;
    }

    public Long getChatId() {
        return chatId;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getRegisteredAt() {
        return userTime;
    }

    public void setRegisteredAt(Timestamp registeredAt) {
        this.userTime = registeredAt;
    }

    @Override
    public String toString() {
        return "Пользователь" +
                "  " + chatId + " " + userAge +
                " "  + userName +
                "/ " + userTime;
    }

    public void setChatId(Long chatId) { this.chatId = chatId;
    }
}
