package com.example.catsanddogstelegram.entity;

import com.example.catsanddogstelegram.model.Animal;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Dog extends Animal {

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
