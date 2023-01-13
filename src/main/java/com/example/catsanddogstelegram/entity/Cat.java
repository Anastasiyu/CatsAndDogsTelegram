package com.example.catsanddogstelegram.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "cats")
public class Cat extends Animal{
}
