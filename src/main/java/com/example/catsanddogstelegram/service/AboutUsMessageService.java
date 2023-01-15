package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.constants.CatShelterDescription;
import com.example.catsanddogstelegram.constants.DogShelterDescription;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class AboutUsMessageService {
    private final TelegramBot telegramBot;
    private final UserService userService;

    public void infoCommandReceived(long chatId) {
        log.debug("method infoCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, DogShelterDescription.INFO.getTypeOfInfoAboutShelter());
        }else{
            sendMessage(chatId, CatShelterDescription.INFO.getTypeOfInfoAboutShelter());
        }
    }

    /**
     * Вывод константного меню TIME_TEXT для ознакомления пользователя с графиком работы приюта
     *
     * @param chatId идентификатор чата для определения ботом кому отвечать
     */
    public void timeCommandReceived(long chatId) {
        log.debug("method timeCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, DogShelterDescription.OPENING_HOURS.getTypeOfInfoAboutShelter());
        }else{
            sendMessage(chatId, CatShelterDescription.OPENING_HOURS.getTypeOfInfoAboutShelter());
        }
    }

    /**
     * Вывод константного меню ADDRESS_TEXT_DOG для ознакомления пользователя с адресом приюта
     *
     * @param chatId идентификатор чата для определения ботом кому отвечать
     */
    public void addressCommandReceived(long chatId) {
        log.debug("method addressCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, DogShelterDescription.ADDRESS.getTypeOfInfoAboutShelter());
        }else{
            sendMessage(chatId, CatShelterDescription.ADDRESS.getTypeOfInfoAboutShelter());
        }
    }

    public void contactsCommandReceived(long chatId) {
        log.debug("method contactsCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, DogShelterDescription.CONTACTS.getTypeOfInfoAboutShelter());
        }else{
            sendMessage(chatId, CatShelterDescription.CONTACTS.getTypeOfInfoAboutShelter());
        }
    }

    public void safetyCommandReceived(long chatId) {
        log.debug("method contactsCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, DogShelterDescription.SAFETY_MEASURES.getTypeOfInfoAboutShelter());
        }else{
            sendMessage(chatId, CatShelterDescription.SAFETY_MEASURES.getTypeOfInfoAboutShelter());
        }
    }

    private void sendMessage(long chatId, String textToSend) {
        log.debug("method sendMessage started");
        SendMessage message = new SendMessage(chatId, textToSend);
        telegramBot.execute(message);
    }
}
