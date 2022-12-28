package com.example.catsanddogstelegram.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Dog extends Animal{

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
