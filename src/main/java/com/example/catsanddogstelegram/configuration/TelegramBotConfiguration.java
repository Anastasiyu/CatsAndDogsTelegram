package com.example.catsanddogstelegram.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfiguration {
    @Value("${telegram.bot.token}")
    private String token;

    @Bean
    public TelegramBot telegramBot(){
        TelegramBot telegramBot = new TelegramBot(token);
        telegramBot.execute(new DeleteMyCommands());
        return telegramBot;
    }
}
