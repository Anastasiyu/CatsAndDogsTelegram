package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.constants.CatShelterDescription;
import com.example.catsanddogstelegram.constants.DogShelterDescription;
import com.example.catsanddogstelegram.repository.UserRepository;
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

    /**
     * Вывод константы {@link DogShelterDescription#INFO}, {@link CatShelterDescription#INFO} для ознакомления
     * пользователя с информацией о выбранном приюте
     * Для проверки приюта происходит обращение к {@link UserRepository#findRequestStatus(long)}
     * @param chatId - идентификатор чата, из которого пришел update
     */
    public void infoCommandReceived(long chatId) {
        log.debug("method infoCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, DogShelterDescription.INFO.getTypeOfInfoAboutShelter());
        }else{
            sendMessage(chatId, CatShelterDescription.INFO.getTypeOfInfoAboutShelter());
        }
    }

    /**
     * Вывод константы {@link DogShelterDescription#OPENING_HOURS}, {@link CatShelterDescription#OPENING_HOURS}
     * для ознакомления с графиком работы приюта
     * Для проверки приюта происходит обращение к {@link UserRepository#findRequestStatus(long)}
     * @param chatId - идентификатор чата, из которого пришел update
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
     * Вывод константы {@link DogShelterDescription#ADDRESS}, {@link CatShelterDescription#ADDRESS} для ознакомления
     * с адресом приюта
     * Для проверки приюта происходит обращение к {@link UserRepository#findRequestStatus(long)}
     * @param chatId - идентификатор чата, из которого пришел update
     */
    public void addressCommandReceived(long chatId) {
        log.debug("method addressCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, DogShelterDescription.ADDRESS.getTypeOfInfoAboutShelter());
        }else{
            sendMessage(chatId, CatShelterDescription.ADDRESS.getTypeOfInfoAboutShelter());
        }
    }

    /**
     * Вывод константы {@link DogShelterDescription#CONTACTS}, {@link CatShelterDescription#CONTACTS} для ознакомления
     * с контактами охраны приюта
     * Для проверки приюта происходит обращение к {@link UserRepository#findRequestStatus(long)}
     * @param chatId - идентификатор чата, из которого пришел update
     */
    public void contactsCommandReceived(long chatId) {
        log.debug("method contactsCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, DogShelterDescription.CONTACTS.getTypeOfInfoAboutShelter());
        }else{
            sendMessage(chatId, CatShelterDescription.CONTACTS.getTypeOfInfoAboutShelter());
        }
    }

    /**
     * Вывод константы {@link DogShelterDescription#SAFETY_MEASURES}, {@link CatShelterDescription#SAFETY_MEASURES}
     * для ознакомления с правиломи безопасности приюта
     * Для проверки приюта происходит обращение к {@link UserRepository#findRequestStatus(long)}
     * @param chatId - идентификатор чата, из которого пришел update
     */
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
