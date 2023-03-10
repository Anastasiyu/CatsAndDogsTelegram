package com.example.catsanddogstelegram.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfiguration {
    /**поле token
     * аннотация {@Value} с указанием названия параметра telegram.bot.token
     * указывает где находиться данный параметр (application.properties)
     */
    @Value("${telegram.bot.token}")
    private String token;

    /**Метод вывода константных запросов в поле телеграмм
     * Принимает {@Value token} бота и с помощью команды execute (исполнять) создает активные команды в поле меню телеграмм бота
     * @return возвращает список команд
     */
    @Bean
    public TelegramBot telegramBot(){
        TelegramBot telegramBot = new TelegramBot(token);
        telegramBot.execute(new SetMyCommands(
                new BotCommand("/start", "get a welcome message"),
                new BotCommand("/time", "opening hours of the shelter"),
                new BotCommand("/addressDog", "address dog of the shelter"),
                new BotCommand("/dog", "how to take a dog from a shelter"),
                new BotCommand("/reportDog", "report on the life of your pet"),
                new BotCommand("/volunteer", "call a volunteer"),
                new BotCommand("/helpDog", "info dog how to use this bot"),
                new BotCommand("/register", "get new user"),
                new BotCommand("/helpCat", "info cat how to use this bot"),
                new BotCommand("/addressCat", "address cat of the shelter"),
                new BotCommand("/cat", "how to take a dog from a shelter"),
                new BotCommand("/reportCat", "report on the life of your pet")));

        return telegramBot;
    }
}
