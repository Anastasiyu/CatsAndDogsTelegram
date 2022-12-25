package com.example.catsanddogstelegram.model;


import javax.persistence.*;
import java.text.SimpleDateFormat;


@org.hibernate.annotations.Entity
@Table(name = "dogs")
public class Dog extends Animals{

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chatId")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dog(Long animalId,
               SimpleDateFormat registerAnimals,
               String animalName,
               int animalAge,
               String description,
               User user) {
        super(animalId, registerAnimals, animalName, animalAge, description);
        this.user = user;
    }

    public Dog() {
        super();
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
