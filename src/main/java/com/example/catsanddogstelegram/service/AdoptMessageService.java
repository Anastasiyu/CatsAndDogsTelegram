package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.constants.PreparingInfoCat;
import com.example.catsanddogstelegram.constants.PreparingInfoDog;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class AdoptMessageService {
    private final TelegramBot telegramBot;
    private final UserService userService;
    public void meetCommandReceived(long chatId) {
        log.debug("method infoCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, PreparingInfoDog.DOG_RULES_OF_DATING.getTypeOfInfo());
        }else{
            sendMessage(chatId, PreparingInfoCat.CAT_RULES_OF_DATING.getTypeOfInfo());
        }
    }

    public void docsCommandReceived(long chatId) {
        log.debug("method infoCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, PreparingInfoDog.DOG_RULES_OF_DATING.getTypeOfInfo());
        }else{
            sendMessage(chatId, PreparingInfoCat.CAT_RULES_OF_DATING.getTypeOfInfo());
        }
    }

    public void transportCommandReceived(long chatId) {
        log.debug("method infoCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, PreparingInfoDog.DOG_RULES_OF_DATING.getTypeOfInfo());
        }else{
            sendMessage(chatId, PreparingInfoCat.CAT_RULES_OF_DATING.getTypeOfInfo());
        }
    }

    public void preparingCommandReceived(long chatId) {
        log.debug("method infoCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, PreparingInfoDog.PREPARING_HOUSE_FOR_A_PUPPY_DOG.getTypeOfInfo());
            sendMessage(chatId, PreparingInfoDog.PREPARING_HOUSE_FOR_AN_ADULT_DOG.getTypeOfInfo());
            sendMessage(chatId, PreparingInfoDog.PREPARING_HOUSE_FOR_A_DISABLED_DOG.getTypeOfInfo());
        }else{
            sendMessage(chatId, PreparingInfoCat.PREPARING_HOUSE_FOR_A_KITTY.getTypeOfInfo());
            sendMessage(chatId, PreparingInfoCat.PREPARING_HOUSE_FOR_AN_ADULT_CAT.getTypeOfInfo());
            sendMessage(chatId, PreparingInfoCat.PREPARING_HOUSE_FOR_A_DISABLED_CAT.getTypeOfInfo());
        }
    }

    public void cynologistCommandReceived(long chatId) {
        log.debug("method infoCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, PreparingInfoDog.DOG_HANDLERS_ADVICE.getTypeOfInfo());
            sendMessage(chatId, PreparingInfoDog.VERIFIED_DOG_HANDLERS.getTypeOfInfo());
        }else{
            sendMessage(chatId, "Извините, данная команда не поддерживается!");
        }
    }

    public void refuseCommandReceived(long chatId) {
        log.debug("method infoCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, PreparingInfoDog.REASONS_FOR_REFUSAL.getTypeOfInfo());
        }else{
            sendMessage(chatId, PreparingInfoCat.REASONS_FOR_REFUSAL.getTypeOfInfo());
        }
    }

    private void sendMessage(long chatId, String textToSend) {
        log.debug("method sendMessage started");
        SendMessage message = new SendMessage(chatId, textToSend);
        telegramBot.execute(message);
    }
}
