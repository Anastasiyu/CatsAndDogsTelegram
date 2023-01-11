package com.example.catsanddogstelegram.listener;

import com.example.catsanddogstelegram.service.TelegramMessageService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
@Data
public class TelegramUpdatesListener implements UpdatesListener {
    private final TelegramBot telegramBot;
    private final TelegramMessageService telegramMessageService;


    @PostConstruct
    public void init() {
        log.debug("method init started");
        telegramBot.setUpdatesListener(this);
    }

    /**Основной метод библиотеки {@link UpdatesListener} который проверяет (слушает) нет ли команд от пользователя
     * в этом методе реализуется структура запросов и ответов бота
     * @param updates параметр библиотеки {@link UpdatesListener} принимающий обновления команд от пользователя
     * @return возвращает полученную по запросу строку ответа в соответствующий чат
     */
    @Override
    public int process(List<Update> updates) {
        log.debug("method process started");
        updates.forEach(update -> {
            log.info("Processing update: {}", update);
            if(update.message() != null) {
                if (update.message().text() == null || update.message().chat() == null) {
                    log.debug("received message without any text or chat info: {}", update);
                    return;
                }
                String message = update.message().text();
                long chatId = update.message().chat().id();
                switch (message) {
                    case "/start":
                        telegramMessageService.startCommandReceived(chatId, update.message().chat().firstName());
                        break;
                    case "/time":
                        telegramMessageService.timeCommandReceived(chatId);
                        break;
                    case "/addressDog":
                        telegramMessageService.addressCommandReceivedDog(chatId);
                        break;
                    case "/addressCat":
                        telegramMessageService.addressCommandReceivedCat(chatId);
                        break;
                    case "/helpDog":
                        telegramMessageService.helpCommandReceivedDog(chatId);
                        break;
                    case "/helpCat":
                        telegramMessageService.helpCommandReceivedCat(chatId);
                        break;
                    case "/register":
                        break;
                    default:
                        telegramMessageService.defaultCommandReceived(chatId);
                }
            }
        });
        return com.pengrad.telegrambot.UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
