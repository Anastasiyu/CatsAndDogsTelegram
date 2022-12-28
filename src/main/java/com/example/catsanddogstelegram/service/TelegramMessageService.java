package com.example.catsanddogstelegram.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Data
public class TelegramMessageService{
    private final TelegramBot telegramBot;
    private final String HELP_TEXT =
            "Этот бот создан для ответов на популярные вопросы людей о том, что нужно знать и уметь, чтобы забрать животное из приюта.\n\n"
            + "Вы можете выполнять команды из главного меню слева или набрав команду:\n\n"
            + "Введите /start чтобы увидеть приветственное сообщение\n\n"
            + "Введите /time чтобы увидеть время работы приюта\n\n"
            + "Введите /address чтобы увидеть адрес приюта\n\n"
            + "Введите /dog чтобы узнать как взять собаку из приюта\n\n"
            + "Введите /report чтобы отправить отчет о жизни у вас питомца\n\n"
            + "Введите /volunteer если ни один из вариантов меню не подходит позвать волонтера\n\n"
            + "Введите /register чтобы зарегистрироваться\n\n"
            + "Введите /help чтобы снова увидеть это сообщение";
    private final String TIME_TEXT = "Время работы приюта: пн-пт с 8-00 до 19-00, сб-вс с 10-00 до 15-00 ";
    private final String ADDRESS_TEXT = "Наш адрес: ул. Ленина, дом 123 ";
    private final String ERROR_TEXT = "Error occurred: ";

    /**Вывод приветственного сообщения с именем пользователя
     *
     * @param chatId идентификатор чата для определения ботом кому отвечать
     * @param name имя пользователя который бот берет из телеграмм чата
     */
    //нужно написать приветственное сообщение с рассказом о себе
    public void startCommandReceived(long chatId, String name) {
        log.debug("method startCommandReceived started");
        String answer = "Привет " + name + ", рад помочь Вам!";
        log.info("Ответил пользователю " + name);
        sendMessage(chatId, answer);
    }

    /**Вывод константного меню HELP_TEXT для ознакомления пользователя с возможными командами бота
     *
     * @param chatId идентификатор чата для определения ботом кому отвечать
     */

    public void helpCommandReceived(long chatId) {
        log.debug("method helpCommandReceived started");
        sendMessage(chatId, HELP_TEXT);
    }

    /**Вывод константного меню ADDRESS_TEXT для ознакомления пользователя с адресом приюта
     *
     * @param chatId идентификатор чата для определения ботом кому отвечать
     */

    public void addressCommandReceived(long chatId) {
        log.debug("method addressCommandReceived started");
        sendMessage(chatId, ADDRESS_TEXT);
    }

    /**Вывод константного меню TIME_TEXT для ознакомления пользователя с графиком работы приюта
     *
     * @param chatId идентификатор чата для определения ботом кому отвечать
     */

    public void timeCommandReceived(long chatId) {
        log.debug("method timeCommandReceived started");
        sendMessage(chatId, TIME_TEXT);
    }

    /**Метод для отправки сообщений ботом
     * метод создает новую строку и определяя по chatId отправляет сообщение пользователю
     * @param chatId идентификатор чата для определения ботом кому отвечать
     * @param textToSend сформированный текст для отправки пользователю
     */
    private void sendMessage(long chatId, String textToSend) {
        log.debug("method sendMessage started");
        SendMessage message = new SendMessage(chatId, textToSend);
        SendResponse response = telegramBot.execute(message);
        if(!response.isOk()){
            log.error("message was not send: {}", response.errorCode());
        }
    }
}
