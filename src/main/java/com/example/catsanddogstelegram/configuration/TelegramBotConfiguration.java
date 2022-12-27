package com.example.catsanddogstelegram.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
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
        telegramBot.execute(new SetMyCommands(
                new BotCommand("/start", "get a welcome message"),
                new BotCommand("/time", "opening hours of the shelter"),
                new BotCommand("/address", "address of the shelter"),
                new BotCommand("/dog", "how to take a dog from a shelter"),
                new BotCommand("/report", "report on the life of your pet"),
                new BotCommand("/volunteer", "call a volunteer"),
                new BotCommand("/help", "info how to use this bot"),
                new BotCommand("/register", "get new user")));
        return telegramBot;
    }
}
