package com.example.catsanddogstelegram.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramUpdatesListener implements UpdatesListener {
    private final TelegramBot telegramBot;
    private final Logger LOGGER = LoggerFactory.getLogger(TelegramUpdatesListener.class);

    public TelegramUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        LOGGER.debug("method init started");
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        LOGGER.debug("method process started");
        updates.forEach(update -> {
            LOGGER.info("Processing update: {}", update);

        });
        return com.pengrad.telegrambot.UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
