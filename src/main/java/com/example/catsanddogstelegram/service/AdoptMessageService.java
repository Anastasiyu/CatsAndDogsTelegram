package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.constants.PreparingInfoCat;
import com.example.catsanddogstelegram.constants.PreparingInfoDog;
import com.example.catsanddogstelegram.repository.UserRepository;
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

    /**
     * Вывод константы {@link PreparingInfoDog#DOG_RULES_OF_DATING}, {@link PreparingInfoCat#CAT_RULES_OF_DATING}
     * для ознакомления пользователя с возможными командами бота в разделе о нас.
     * Для проверки приюта происходит обращение к {@link UserRepository#findShelterTypeByChatId(long)}
     * @param chatId идентификатор чата, из которого пришел update
     */
    public void meetCommandReceived(long chatId) {
        log.debug("method infoCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, PreparingInfoDog.DOG_RULES_OF_DATING.getTypeOfInfo());
        }else{
            sendMessage(chatId, PreparingInfoCat.CAT_RULES_OF_DATING.getTypeOfInfo());
        }
    }

    /**
     * Вывод константы {@link PreparingInfoDog#DOG_RULES_OF_DATING}, {@link PreparingInfoCat#CAT_RULES_OF_DATING}
     * для ознакомления с правилами знакомства с животными в приюте.
     * Для проверки приюта происходит обращение к {@link UserRepository#findShelterTypeByChatId(long)}
     * @param chatId идентификатор чата, из которого пришел update
     */
    public void docsCommandReceived(long chatId) {
        log.debug("method infoCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, PreparingInfoDog.DOG_RULES_OF_DATING.getTypeOfInfo());
        }else{
            sendMessage(chatId, PreparingInfoCat.CAT_RULES_OF_DATING.getTypeOfInfo());
        }
    }

    /**
     * Вывод константы {@link PreparingInfoDog#TRANSPORTATION_ADVICE}, {@link PreparingInfoCat#TRANSPORTATION_ADVICE}
     * для ознакомления с правилами перевозки.
     * Для проверки приюта происходит обращение к {@link UserRepository#findShelterTypeByChatId(long)}
     * @param chatId идентификатор чата, из которого пришел update
     */
    public void transportCommandReceived(long chatId) {
        log.debug("method infoCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, PreparingInfoDog.TRANSPORTATION_ADVICE.getTypeOfInfo());
        }else{
            sendMessage(chatId, PreparingInfoCat.TRANSPORTATION_ADVICE.getTypeOfInfo());
        }
    }

    /**
     * Вывод константы {@link PreparingInfoDog#PREPARING_HOUSE_FOR_A_PUPPY_DOG},
     * {@link PreparingInfoCat#PREPARING_HOUSE_FOR_A_KITTY}
     * для ознакомления с правиломи обустройста места.
     * Для проверки приюта происходит обращение к {@link UserRepository#findShelterTypeByChatId(long)}
     * @param chatId идентификатор чата, из которого пришел update
     */
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

    /**
     * Вывод константы {@link PreparingInfoDog#DOG_HANDLERS_ADVICE}, {@link PreparingInfoDog#VERIFIED_DOG_HANDLERS}
     * для ознакомления с проверенными кинологами.
     * Для проверки приюта происходит обращение к {@link UserRepository#findShelterTypeByChatId(long)}
     * @param chatId идентификатор чата, из которого пришел update
     */
    public void cynologistCommandReceived(long chatId) {
        log.debug("method infoCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, PreparingInfoDog.DOG_HANDLERS_ADVICE.getTypeOfInfo());
            sendMessage(chatId, PreparingInfoDog.VERIFIED_DOG_HANDLERS.getTypeOfInfo());
        }else{
            sendMessage(chatId, "Извините, данная команда не поддерживается!");
        }
    }

    /**
     * Вывод константы {@link PreparingInfoDog#REASONS_FOR_REFUSAL}, {@link PreparingInfoCat#REASONS_FOR_REFUSAL}
     * для ознакомления с возможными причинами отказа.
     * Для проверки приюта происходит обращение к {@link UserRepository#findShelterTypeByChatId(long)}
     * @param chatId идентификатор чата, из которого пришел update
     */
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
