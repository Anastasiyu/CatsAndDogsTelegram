package com.example.catsanddogstelegram.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.catsanddogstelegram.constants.MenuTexts.*;

@Service
@Slf4j
@Data
public class TelegramMessageService {
    private final TelegramBot telegramBot;
    private final UserService userService;

    /**
     * Вывод приветственного сообщения с именем пользователя
     *
     * @param chatId идентификатор чата для определения ботом кому отвечать
     * @param name   имя пользователя который бот берет из телеграмм чата
     */
    public void startCommandReceived(long chatId, String name) {
        log.debug("method startCommandReceived started");
        String answer = "Привет " + name + ", рад помочь Вам!" +
                " Для начала выберите приют к которому хотите обратиться.\n" +
                "/dog - приют для собак\n" +
                "/cat - приют для кошек";
        log.info("Ответил пользователю " + name);
        sendMessage(chatId, answer);
    }

    public void ShelterCommandReceived(long chatId) {
        log.debug("method ShelterCommandReceived started");
        sendMessage(chatId, "/aboutUs - узнать больше о приюте\n" +
                "/adopt - как взять питомца\n" +
                "/report - отправить отчет\n" +
                "/volunteer - позвать волонтера");
    }

    /**
     * Вывод константного меню HELP_TEXT_DOG для ознакомления пользователя с возможными командами бота
     * если хотят взять собаку
     * @param chatId идентификатор чата для определения ботом кому отвечать
     */
    public void aboutUsCommandReceived(long chatId) {
        log.debug("method aboutUsCommandReceived started");
        sendMessage(chatId, ABOUT_US_TEXT.getMessage());
    }

    public void adoptCommandReceived(long chatId) {
        log.debug("method adoptCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, ADOPT_TEXT_DOG.getMessage());
        }else{
            sendMessage(chatId, ADOPT_TEXT_CAT.getMessage());
        }
    }

    public void reportCommandReceived(long chatId) {
        log.debug("method reportCommandReceived started");
    }

    public void registerCommandReceived(long chatId) {
        log.debug("method registerCommandReceived started");
        userService.setUser(chatId, true);
        SendMessage message = new SendMessage(chatId, "Введите ваш номер телефона")
                .replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("отмена").callbackData("/cancel")));
        telegramBot.execute(message);
    }

    public void registerVerify(long chatId, String message) {
        log.debug("method registerVerify started");
        if(message.matches("^\\+\\d{11}$|^\\d{11}$")){
            userService.setUser(chatId, message.trim());
            userService.setUser(chatId, false);
            sendMessage(chatId, "Сохранено, скоро свяжемся");
        } else {
            sendMessage(chatId, "Неверный формат номера, попробуйте ещё раз");
        }
    }

    public void cancelCommandReceived(long chatId) {
        log.debug("method cancelCommandReceived started");
        userService.setUser(chatId, false);
        sendMessage(chatId, "В другой раз");
    }

    /**
     * Вывод константного меню DEFAULT_TEXT при запросе несуществующей команды
     *
     * @param chatId идентификатор чата для определения ботом кому отвечать
     */
    public void defaultCommandReceived(long chatId) {
        log.debug("method timeCommandReceived started");
        sendMessage(chatId, DEFAULT_TEXT.getMessage());
    }

    /**
     * Метод для отправки сообщений ботом
     * метод создает новую строку и определяя по chatId отправляет сообщение пользователю
     *
     * @param chatId     идентификатор чата для определения ботом кому отвечать
     * @param textToSend сформированный текст для отправки пользователю
     */
    private void sendMessage(long chatId, String textToSend) {
        log.debug("method sendMessage started");
        SendMessage message = new SendMessage(chatId, textToSend);
        telegramBot.execute(message);
    }
}
