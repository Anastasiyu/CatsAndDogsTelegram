package com.example.catsanddogstelegram.listener;

import com.example.catsanddogstelegram.service.TelegramMessageService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
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

    @Override
    public int process(List<Update> updates) {
        log.debug("method process started");
        updates.forEach(update -> {
            log.info("Processing update: {}", update);
            String message = update.message().text();
            long chatId = update.message().chat().id();
            switch (message) {
                case "/start":
                    telegramMessageService.startCommandReceived(chatId, update.message().chat().firstName());
                    break;
                case "/time":
                    telegramMessageService.timeCommandReceived(chatId);
                    break;
                case "/address":
                    telegramMessageService.addressCommandReceived(chatId);
                    break;
                case "/help":
                    telegramMessageService.helpCommandReceived(chatId);
                    break;
                case "/register":
                    break;
                default:
                    sendMessage(chatId, "Извините, данная команда не поддерживается!");
            }
        });
        return com.pengrad.telegrambot.UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendMessage(long chatId, String textToSend) {
        log.debug("method sendMessage started");
        SendMessage message = new SendMessage(chatId, textToSend);
        SendResponse response = telegramBot.execute(message);
        if(!response.isOk()){
            log.error("message was not send: {}", response.errorCode());
        }
    }
}
