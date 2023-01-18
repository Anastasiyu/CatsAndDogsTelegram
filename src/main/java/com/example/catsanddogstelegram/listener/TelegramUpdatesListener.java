package com.example.catsanddogstelegram.listener;

import com.example.catsanddogstelegram.exception.UserNotFoundException;
import com.example.catsanddogstelegram.service.*;
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
    private final ReportMessageService reportMessageService;
    private final VolunteerService volunteerService;
    private final UserService userService;
    private final CatAdopterService catAdopterService;
    private final DogAdopterService dogAdopterService;


    @PostConstruct
    public void init() {
        log.debug("method init started");
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Основной метод библиотеки {@link UpdatesListener} который проверяет (слушает) нет ли команд от пользователя
     * в этом методе реализуется структура запросов и ответов бота
     *
     * @param updates параметр библиотеки {@link UpdatesListener} принимающий обновления команд от пользователя
     * @return возвращает полученную по запросу строку ответа в соответствующий чат
     */
    @Override
    public int process(List<Update> updates) {
        log.debug("method process started");
        updates.forEach(update -> {
            log.info("Processing update: {}", update);
            if (update.message() != null) {
                long chatId = update.message().chat().id();
                if (userService.readUser(chatId) == null) {
                    userService.createUser(chatId, Timestamp.valueOf(LocalDateTime.now()),
                            update.message().from().firstName());
                }
                if(userService.getShelterType(chatId) == 0){
                    telegramMessageService.startCommandReceived(chatId, update.message().chat().firstName());
                }

                try{
                    dogAdopterService.readDogAdopter(chatId);
                    if (dogAdopterService.getRequestStatus(chatId)) {
                        reportMessageService.reportUpdateListener(update, chatId);
                        return;
                    }
                }catch (UserNotFoundException ignored){}
                try{
                    catAdopterService.readCatAdopter(chatId);
                    if (catAdopterService.getRequestStatus(chatId)) {
                        reportMessageService.reportUpdateListener(update, chatId);
                        return;
                    }
                }catch (UserNotFoundException ignored){}

                if (update.message().text() == null) {
                    log.debug("received message without any text or photo info: {}", update);
                    return;
                }
                String message = update.message().text();
                if (userService.getRequestStatus(chatId)) {
                    telegramMessageService.registerVerify(chatId, message);
                    return;
                }

                switch (message) {
                    case "/start":
                        telegramMessageService.startCommandReceived(chatId, update.message().chat().firstName());
                        break;
                    case "/dog":
                        userService.setUser(chatId, 1);
                        telegramMessageService.ShelterCommandReceived(chatId);
                        break;
                    case "/cat":
                        userService.setUser(chatId, 2);
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
                        volunteerService.volunteerCommandReceived(chatId, update.message().from().username());
                        break;
                    case "/register":
                        telegramMessageService.registerCommandReceived(chatId);
                        break;
                    //волонтерское
                    case "/on":
                        volunteerService.onCommandReceived(chatId);
                        break;
                    case "/off":
                        volunteerService.offCommandReceived(chatId);
                        break;
                    default:
                        telegramMessageService.defaultCommandReceived(chatId);
                }
            }
            if (update.callbackQuery() != null) {
                String data = update.callbackQuery().data();
                long chatId = update.callbackQuery().message().chat().id();
                switch (data) {
                    case "/cancel":
                        telegramMessageService.cancelCommandReceived(chatId);
                        break;
                    case "report cancel":
                        reportMessageService.cancelCommandReceived(chatId);
                        break;
                }
            }
        });
        return com.pengrad.telegrambot.UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
