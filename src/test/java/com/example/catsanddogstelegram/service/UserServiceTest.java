package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.User;
import com.example.catsanddogstelegram.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void findUserByName() {
    }

    @Test
    void createUserTest() {
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        User user = new User();
        user.setUserName("Иван");
        user.setChatId(167L);
        user.setUserTime(time);
        user.setType(0);
        user.setStatus(false);

        when(userRepository.findById(user.getChatId())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        Assertions.assertThat(userService.createUser(user.getChatId(),
                time, user.getUserName())).isEqualTo(user);
    }

    @Test
    void setUserTest() {
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        User user = new User();
        user.setUserName("Иван");
        user.setChatId(167L);
        user.setUserTime(time);
        user.setType(0);
        user.setStatus(false);

        User test = new User();
        test.setUserName("Иван");
        test.setChatId(167L);
        test.setUserTime(time);
        test.setType(1);
        test.setStatus(true);
        test.setPhoneNumber("88888888");

        when(userRepository.findById(user.getChatId())).thenReturn(Optional.of(user));

        userService.setUser(user.getChatId(), 1);
        userService.setUser(user.getChatId(), "88888888");
        userService.setUser(user.getChatId(), true);

        assertThat(userService.setUser(user.getChatId(), 1)).isEqualTo(1);
        assertThat(userService.setUser(user.getChatId(), true)).isEqualTo(true);
        assertThat(user).isEqualTo(test);
    }

    @Test
    void getShelterTypeTest() {
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        User user = new User();
        user.setUserName("Иван");
        user.setChatId(167L);
        user.setUserTime(time);
        user.setType(1);
        user.setStatus(false);

        when(userRepository.findShelterTypeByChatId(user.getChatId())).thenReturn(Optional.of(user.getType()));

        assertThat(userService.getShelterType(user.getChatId())).isEqualTo(1);
    }

    @Test
    void getRequestStatusTest() {
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        User user = new User();
        user.setUserName("Иван");
        user.setChatId(167L);
        user.setUserTime(time);
        user.setType(1);
        user.setStatus(false);

        when(userRepository.findRequestStatus(user.getChatId())).thenReturn(Optional.of(user.isStatus()));

        assertThat(userService.getRequestStatus(user.getChatId())).isEqualTo(false);
    }

    @Test
    void readUserTest(){
        User user = new User();

        when(userRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(user));

        assertThat(userService.readUser(123L)).isEqualTo(null);
        assertThat(userService.readUser(123L)).isEqualTo(user);
    }
}