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

    /**Метод вывода константных запросов в поле телеграмм
     * С помощью команды execute (исполнять) создает активные команды в поле меню телеграмм бота
     * @return возвращает список команд
     */
    @Bean
    public TelegramBot telegramBot(){
        TelegramBot telegramBot = new TelegramBot(token);
        telegramBot.execute(new SetMyCommands(
                new BotCommand("/start", "Начало работы бота и выбор приюта")
        ));
        return telegramBot;
    }
}
