package com.example.catsanddogstelegram.service;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ExtendWith(MockitoExtension.class)
public class TelegramMessageServiceTest {

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
    private final String DEFAULT_TEXT = "Извините, данная команда не поддерживается!\nCписок команд /info";
    private final String ERROR_TEXT = "Error occurred: ";


    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private TelegramMessageService telegramMessageService;


    @Test
    public void startCommandReceivedTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("text_update_service.json").toURI()));
        Update update = getUpdate(json, "/start");
        telegramMessageService.startCommandReceived(update.updateId(), update.message().chat().firstName());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        org.assertj.core.api.Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        org.assertj.core.api.Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("Привет Александр, рад помочь Вам!");
    }

    private Update getUpdate(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%command%", replaced), Update.class);
    }

    @Test
    void helpCommandReceived() {
    }

    @Test
    void addressCommandReceived() {
    }

    @Test
    void timeCommandReceived() {
    }

    @Test
    void defaultCommandReceived() {
    }


}