package com.example.catsanddogstelegram.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Data
public class TelegramMessageService {
    private final TelegramBot telegramBot;
    private final UserService userService;
    private final String ABOUT_US_TEXT =
                    "В разделе о нас вы можете больше о нашем приюте:\n\n"
                    + "/info - узнать о приюте\n\n"
                    + "/time - время работы приюта\n\n"
                    + "/contacts - наши контакты\n\n"
                    + "/address - адрес приюта\n\n"
                    + "/safety - правила безопасности\n\n"
                    + "/register - отправить контактные данные\n\n"
                    + "/volunteer - позвать волонтера\n\n"
                    + "/choice вернуться к выбору приюта";

    private final String ADOPT_TEXT_DOG =
            "В разделе о нас вы можете больше о нашем приюте:\n\n"
                    + "/meet - правила знакомства с животным до того, как забрать его из приюта\n\n"
                    + "/docs - список документов, необходимых для того, чтобы взять животное из приюта\n\n"
                    + "/transport - список рекомендаций по транспортировке животного\n\n"
                    + "/preparing - список рекомендаций  по обустройству дома для животного\n\n"
                    + "/cynologist - советы кинолога по первичному общению с собакой\n\n"
                    + "/refuse - список причин, почему могут отказать и не дать забрать собаку из приюта\n\n"
                    + "/register - отправить контактные данные\n\n"
                    + "/volunteer - позвать волонтера\n\n"
                    + "/choice - вернуться к выбору приюта";

    private final String ADOPT_TEXT_CAT =
            "В разделе о нас вы можете больше о нашем приюте:\n\n"
                    + "/meet - правила знакомства с животным до того, как забрать его из приюта\n\n"
                    + "/docs - список документов, необходимых для того, чтобы взять животное из приюта\n\n"
                    + "/transport - список рекомендаций по транспортировке животного\n\n"
                    + "/preparing - список рекомендаций  по обустройству дома для животного\n\n"
                    + "/register - отправить контактные данные\n\n"
                    + "/volunteer - позвать волонтера\n\n"
                    + "/choice - вернуться к выбору приюта";
    private final String DEFAULT_TEXT = "Извините, данная команда не поддерживается!";

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
        sendMessage(chatId, ABOUT_US_TEXT);
    }

    public void adoptCommandReceived(long chatId) {
        log.debug("method adoptCommandReceived started");
        if(userService.getShelterType(chatId) == 1){
            sendMessage(chatId, ADOPT_TEXT_DOG);
        }else{
            sendMessage(chatId, ADOPT_TEXT_CAT);
        }
    }

    public void reportCommandReceived(long chatId) {
        log.debug("method reportCommandReceived started");
    }

    public void volunteerCommandReceived(long chatId) {
        log.debug("method volunteerCommandReceived started");
    }

    public void registerCommandReceived(long chatId) {
        log.debug("method registerCommandReceived started");
        userService.setUser(chatId, true);
        sendMessage(chatId, "Введите ваш номер телефона");
    }

    public void registerVerify(long chatId, String message) {
        if(message.matches("^\\+\\d{11}$|^\\d{11}$")){
            userService.setUser(chatId, message.trim());
            userService.setUser(chatId, false);
            sendMessage(chatId, "Сохранено, скоро свяжемся");
        } else {
            sendMessage(chatId, "неверный формат номера, попробуйте ещё раз");
        }
    }

    /**
     * Вывод константного меню DEFAULT_TEXT при запросе несуществующей команды
     *
     * @param chatId идентификатор чата для определения ботом кому отвечать
     */
    public void defaultCommandReceived(long chatId) {
        log.debug("method timeCommandReceived started");
        sendMessage(chatId, DEFAULT_TEXT);
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
