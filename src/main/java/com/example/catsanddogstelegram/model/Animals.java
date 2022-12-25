package com.example.catsanddogstelegram.model;

import javax.persistence.Id;
import java.text.SimpleDateFormat;

public class Animals {
    public Animals(Long animalId,
                   SimpleDateFormat registerAnimals,
                   String animalName,
                   int animalAge,
                   String description) {
        this.animalId = animalId;
        this.registerAnimals = registerAnimals;
        this.animalName = animalName;
        this.animalAge = animalAge;
        this.description = description;
    }

    @Id
    private Long animalId;

    private SimpleDateFormat registerAnimals; // дата поступления животного в приют
    private String animalName;

    private int animalAge;

    private String description;

    public Animals() {

    }

    public int getAnimalAge() {
        return animalAge;
    }

    public void setAnimalAge(int animalAge) {
        this.animalAge = animalAge;
    }

    public SimpleDateFormat getRegisterAnimals() {
        return registerAnimals;
    }

    public void setRegisterAnimals(SimpleDateFormat registerAnimals) {
        this.registerAnimals = registerAnimals;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAnimalId() {
        return animalId;

    }

    public void setAnimalId(Long animalId) {
        this.animalId = animalId;
    }

    @Override
    public String toString() {
        return "Animals" +
                " " + animalId +
                "/ " + registerAnimals +
                ", " + animalName +
                ", " + animalAge +
                ", описание" + description;
    }
}
