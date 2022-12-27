package com.example.catsanddogstelegram.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "animals")
@Data
public abstract class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private Integer animalId;
    @Column(name = "register_date")
    private Timestamp registerDate;
    @Column(name = "animal_gender")
    private Boolean gender;
    @Column(name = "animal_name")
    private String animalName;
    @Column(name = "animal_age")
    private int animalAge;
    @Column(name = "description")
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(animalId, animal.animalId) && Objects.equals(registerDate, animal.registerDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId, registerDate);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "animalId=" + animalId +
                ", registerDate=" + registerDate +
                ", gender=" + gender +
                ", animalName='" + animalName + '\'' +
                ", animalAge=" + animalAge +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}
