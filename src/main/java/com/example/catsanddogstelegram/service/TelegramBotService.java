package com.example.catsanddogstelegram.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

import java.util.List;


@PropertySource("application.properties")
@Component
@Service
public class TelegramBotService extends TelegramLongPollingBot {
    private final Logger log = LoggerFactory.getLogger(TelegramBotService.class);

    private final String botName;
    private final String botToken;
    static final String HELP_TEXT =
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

    static final String TIME_TEXT = "Время работы приюта: пн-пт с 8-00 до 19-00, сб-вс с 10-00 до 15-00 ";

    static final String ADDRESS_TEXT = "Наш адрес: ул. Ленина, дом 123 ";
    static final String ERROR_TEXT = "Error occurred: ";

    public TelegramBotService(
                              @Value("${telegrem.bot.name}") String botName,
                              @Value("${telegram.bot.token}") String botToken) {

        this.botName = botName;
        this.botToken = botToken;

        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "get a welcome message"));
        listofCommands.add(new BotCommand("/time", "opening hours of the shelter"));
        listofCommands.add(new BotCommand("/address", "address of the shelter"));
        listofCommands.add(new BotCommand("/dog", "how to take a dog from a shelter"));
        listofCommands.add(new BotCommand("/report", "report on the life of your pet"));
        listofCommands.add(new BotCommand("/volunteer", "call a volunteer"));
        listofCommands.add(new BotCommand("/help", "info how to use this bot"));
        listofCommands.add(new BotCommand("/register", "get new user"));
        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Ошибка настройки списка команд бота: " + e.getMessage());
        }

    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public String getBotToken() {

        return this.botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        //проверяет есть текст который нужно обработать
        if (update.hasMessage() && update.getMessage().hasText()) {
            //обработка написанного текста
            String messageText = update.getMessage().getText();
            //присвоение идентификатора чата для бота
            long chatId = update.getMessage().getChatId();

            // в зависимости какая команда получена, выбирает метод, который применить
            switch (messageText) {
                case "/start":

                    startCommandReceived(chatId, update.getMessage().getChat().getUserName());
                    break;

                case "/time":

                    sendMessage(chatId, TIME_TEXT);
                    break;
                case "/address":

                    sendMessage(chatId, ADDRESS_TEXT);
                    break;

                case "/help":

                    sendMessage(chatId, HELP_TEXT);
                    break;

                case "/register":

                    break;

                default:
                    sendMessage(chatId, "Извините, данная команда не поддерживается!");
            }
        }
    }


    private void startCommandReceived(long chatId, String name) {

        String answer = "Привет, рад помочь Вам!";
        log.info("Ответил пользователю ");

        sendMessage(chatId, answer);
    }



    // метод отправить сообщение
    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }




}

