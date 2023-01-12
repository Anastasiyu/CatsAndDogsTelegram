package com.example.catsanddogstelegram.entity;

import lombok.Data;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;

/**Класс пользователисо свойствами <b>chatId</b>, <b>userTime</b>,
 *  <b>userName</b>, <b>userAge</b>
 * @author new developers
 * @version 1.0
 */
@Entity
@Table(name = "users")
@Data
public class User {

    /** Поле идентификационный чата */
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    /** Поле время регистрации пользователя*/
    @Column(name = "user_time")
    private Timestamp userTime;

    /** Поле имя пользователя*/
    @Column(name = "user_name")
    private String userName;
    @Column(name = "shelter_type")
    private int type;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;

//    /**Метод БД определяющий зависимость у одного пользователя много животных*/
//    @OneToMany(mappedBy = "user")
//    private Collection<Animal> animals;

    @Override
    public String toString() {
        return "Пользователь" +
                "  " + chatId  +
                " "  + userName +
                "/ " + userTime;
    }
}
