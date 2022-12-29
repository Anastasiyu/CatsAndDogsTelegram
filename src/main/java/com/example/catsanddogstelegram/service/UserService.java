package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.User;
import com.example.catsanddogstelegram.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
