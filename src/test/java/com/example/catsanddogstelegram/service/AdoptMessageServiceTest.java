package com.example.catsanddogstelegram.service;

import com.pengrad.telegrambot.TelegramBot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class AdoptMessageServiceTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdoptMessageService adoptMessageService;

    @Test
    void meetCommandReceived() {
    }

    @Test
    void docsCommandReceived() {
    }

    @Test
    void transportCommandReceived() {
    }

    @Test
    void preparingCommandReceived() {
    }

    @Test
    void cynologistCommandReceived() {
    }

    @Test
    void refuseCommandReceived() {
    }
}