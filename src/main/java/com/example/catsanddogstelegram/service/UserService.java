package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.User;
import com.example.catsanddogstelegram.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findUserByName(String userName) {
        log.info("method findUserByUserName started");
        if (userName.isBlank()) {
            throw new IllegalArgumentException();
        }
        return userRepository.findUserByUserName(userName);
    }

    public void saveUser(long chatId, Timestamp time, String name){
        User user = userRepository.findById(chatId).orElse(null);
        if(user != null) {
            return;
        }
        user = new User();
        user.setChatId(chatId);
        user.setUserTime(time);
        user.setUserName(name);
        userRepository.save(user);
    }

    public void setUser(long chatId, Integer type){
        User user = userRepository.findById(chatId).orElse(null);
        if(user == null){
            return;
        }
        user.setType(type);
        userRepository.save(user);
    }

    public void setUser(long chatId, String number){
        User user = userRepository.findById(chatId).orElse(null);
        if(user == null){
            return;
        }
        user.setPhoneNumber(number);
        userRepository.save(user);
    }

    public void setUser(long chatId, boolean status){
        User user = userRepository.findById(chatId).orElse(null);
        if(user == null){
            return;
        }
        user.setStatus(status);
        userRepository.save(user);
        log.info("saved " + user.isStatus() + " for " + user);
    }

    public int getShelterType(long chatId) {
        return userRepository.findShelterTypeByChatId(chatId);
    }

    public boolean getRequestStatus(long chatId) {
        return userRepository.findRequestStatus(chatId);
    }
}
