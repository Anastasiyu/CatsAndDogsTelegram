package com.example.catsanddogstelegram.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "dogs")
public class Dog extends Animal{
}