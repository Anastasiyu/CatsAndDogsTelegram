package com.example.catsanddogstelegram.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "dog_reports")
@Data
public class DogReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "chat_id")
    private long chatId;
    @Column(name = "text")
    private String text;
    @Column(name = "file_path")
    private String filePath;
}
