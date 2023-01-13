package com.example.catsanddogstelegram.listener;

import com.example.catsanddogstelegram.service.AboutUsMessageService;
import com.example.catsanddogstelegram.service.AdoptMessageService;
import com.example.catsanddogstelegram.service.TelegramMessageService;
import com.example.catsanddogstelegram.service.UserService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Data
public class TelegramUpdatesListener implements UpdatesListener {
    private final TelegramBot telegramBot;
    private final TelegramMessageService telegramMessageService;
    private final AboutUsMessageService aboutUsMessageService;
    private final AdoptMessageService adoptMessageService;
    private final UserService userService;


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
                if(!message.equals("/start") && userService.getRequestStatus(chatId)){
                    telegramMessageService.registerVerify(chatId, message);
                    return;
                }
                switch (message) {
                    case "/start":
                        userService.saveUser(chatId, Timestamp.valueOf(LocalDateTime.now()), update.message().from().firstName());
                        telegramMessageService.startCommandReceived(chatId, update.message().chat().firstName());
                        break;
                    case "/dog":
                        userService.setUser(chatId, 1);
                        telegramMessageService.ShelterCommandReceived(chatId);
                        break;
                    case "/cat":
                        userService.setUser(chatId,2);
                        telegramMessageService.ShelterCommandReceived(chatId);
                        break;

                    case "/aboutUs": //этап 1
                        telegramMessageService.aboutUsCommandReceived(chatId);
                        break;
                    case "/adopt": //этап 2
                        telegramMessageService.adoptCommandReceived(chatId);
                        break;
                    case "/report": //этап 3
                        telegramMessageService.reportCommandReceived(chatId);
                        break;
                    //этап 1
                    case "/info":
                        aboutUsMessageService.infoCommandReceived(chatId);
                        break;
                    case "/time":
                        aboutUsMessageService.timeCommandReceived(chatId);
                        break;
                    case "/address":
                        aboutUsMessageService.addressCommandReceived(chatId);
                        break;
                    case "/contacts":
                        aboutUsMessageService.contactsCommandReceived(chatId);
                        break;
                    case "/safety":
                        aboutUsMessageService.safetyCommandReceived(chatId);
                        break;
                    //этап 2
                    case "/meet":
                        adoptMessageService.meetCommandReceived(chatId);
                        break;
                    case "/docs":
                        adoptMessageService.docsCommandReceived(chatId);
                        break;
                    case "/transport":
                        adoptMessageService.transportCommandReceived(chatId);
                        break;
                    case "/preparing":
                        adoptMessageService.preparingCommandReceived(chatId);
                        break;
                    case "/cynologist":
                        adoptMessageService.cynologistCommandReceived(chatId);
                        break;
                    case "/refuse":
                        adoptMessageService.refuseCommandReceived(chatId);
                        break;
                    //общее
                    case "/volunteer":
                        telegramMessageService.volunteerCommandReceived(chatId);
                        break;
                    case "/register":
                        telegramMessageService.registerCommandReceived(chatId);
                        break;
                    default:
                        telegramMessageService.defaultCommandReceived(chatId);
                }
            }
            if(update.callbackQuery() != null){
                String data = update.callbackQuery().data();
                long chatId = update.callbackQuery().message().chat().id();
                switch(data){
                    case "/cancel":
                        telegramMessageService.cancelCommandReceived(chatId);
                        break;
                }
            }
        });
        return com.pengrad.telegrambot.UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
