package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.constants.MenuTexts;
import com.example.catsanddogstelegram.repository.UserRepository;
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

    /**
     * Вывод меню для ознакомления пользователя с разделами.
     * @param chatId идентификатор чата, из которого пришел update
     */
    public void ShelterCommandReceived(long chatId) {
        log.debug("method ShelterCommandReceived started");
        sendMessage(chatId, "/aboutUs - узнать больше о приюте\n" +
                "/adopt - как взять питомца\n" +
                "/report - отправить отчет\n" +
                "/volunteer - позвать волонтера");
    }

    /**
     * Вывод константы {@link MenuTexts#ABOUT_US_TEXT} для ознакомления пользователя с возможными командами бота
     * в разделе о нас.
     * @param chatId идентификатор чата, из которого пришел update
     */
    public void aboutUsCommandReceived(long chatId) {
        log.debug("method aboutUsCommandReceived started");
        sendMessage(chatId, ABOUT_US_TEXT.getMessage());
    }

    /**
     * Вывод константы {@link MenuTexts#ADOPT_TEXT_DOG} или {@link MenuTexts#ADOPT_TEXT_CAT} в зависимости оттого,
     * какой приют user выбрал для ознакомления user с возможными командами бота в разделе о нас.
     * Для проверки приюта происходит обращение к {@link UserRepository#findShelterTypeByChatId(long)}
     * @param chatId - идентификатор чата, из которого пришел update
     */
    public void adoptCommandReceived(long chatId) {
        log.debug("method adoptCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, ADOPT_TEXT_DOG.getMessage());
        }else{
            sendMessage(chatId, ADOPT_TEXT_CAT.getMessage());
        }
    }

    /**
     * Вывод константы {@link MenuTexts#REPORT_TEXT} для ознакомления user с возможными командами бота
     * в разделе "отчет".
     * @param chatId - идентификатор чата, из которого пришел update
     */
    public void reportCommandReceived(long chatId) {
        log.debug("method reportCommandReceived started");
    }

    /**
     * Открытие запроса принятия контакта user ботом.
     * Сохранение статуса в БД
     * @param chatId - идентификатор чата, из которого пришел update
     */
    public void registerCommandReceived(long chatId) {
        log.debug("method registerCommandReceived started");
        userService.setUser(chatId, true);
        SendMessage message = new SendMessage(chatId, "Введите ваш номер телефона")
                .replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("отмена").callbackData("/cancel")));
        telegramBot.execute(message);
    }

    /**
     * Проверка формата введенного пользователем сообщения с контактом.
     * Валидный формат +ХХХХХХХХХХХ или ХХХХХХХХХХХ
     * @param chatId - идентификатор чата, из которого пришел update
     */
    public void registerVerify(long chatId, String message) {
        log.debug("method registerVerify started");
        if(message.matches("^\\+?\\d{11}$")){
            userService.setUser(chatId, message.trim());
            userService.setUser(chatId, false);
            sendMessage(chatId, "Сохранено, скоро свяжемся");
        } else {
            sendMessage(chatId, "Неверный формат номера, попробуйте ещё раз");
        }
    }

    /**
     * Обработка нажания кнопки отмены пользователем, закрытие статуса запроса принятия контакта от user.
     * Сохранение статуса в БД
     * @param chatId - идентификатор чата, из которого пришел update
     */
    public void cancelCommandReceived(long chatId) {
        log.debug("method cancelCommandReceived started");
        userService.setUser(chatId, false);
        sendMessage(chatId, "В другой раз");
    }

    /**
     * Вывод константного меню DEFAULT_TEXT при запросе несуществующей команды
     * @param chatId - идентификатор чата, из которого пришел update
     */
    public void defaultCommandReceived(long chatId) {
        log.debug("method defaultCommandReceived started");
        sendMessage(chatId, DEFAULT_TEXT.getMessage());
    }

    /**
     * Метод для отправки сообщений ботом
     * метод создает новую строку и определяя по chatId отправляет сообщение user
     * @param chatId - идентификатор чата, из которого пришел update
     * @param textToSend - сформированный текст для отправки пользователю
     */
    private void sendMessage(long chatId, String textToSend) {
        log.debug("method sendMessage started");
        SendMessage message = new SendMessage(chatId, textToSend);
        telegramBot.execute(message);
    }
}
