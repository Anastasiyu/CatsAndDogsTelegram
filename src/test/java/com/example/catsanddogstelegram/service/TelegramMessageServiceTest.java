package com.example.catsanddogstelegram.service;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.assertj.core.api.Assertions;
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
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
public class TelegramMessageServiceTest {
    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private TelegramMessageService telegramMessageService;

    @Test
    public void startCommandReceivedTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json, "/start");
        telegramMessageService.startCommandReceived(update.message().from().id(), update.message().from().firstName());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("Привет Александр, рад помочь Вам!");
    }

    @Test
    void helpCommandReceivedDog() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json, "/help");
        telegramMessageService.helpCommandReceivedDog(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(telegramMessageService.getHELP_TEXT_DOG());
    }

    @Test
    void helpCommandReceivedCat() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json, "/help");
        telegramMessageService.helpCommandReceivedCat(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(telegramMessageService.getHELP_TEXT_CAT());
    }


    @Test
    void addressCommandReceivedDog() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json, "/address");
        telegramMessageService.addressCommandReceivedDog(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(telegramMessageService.getADDRESS_TEXT_DOG());
    }
    @Test
    void addressCommandReceivedCat() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json, "/address");
        telegramMessageService.addressCommandReceivedCat(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(telegramMessageService.getADDRESS_TEXT_CAT());
    }

    @Test
    void timeCommandReceived()  throws URISyntaxException, IOException  {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json, "/time");
        telegramMessageService.timeCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(telegramMessageService.getTIME_TEXT());
    }

    @Test
    void defaultCommandReceived()  throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json, "/default");
        telegramMessageService.defaultCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(telegramMessageService.getDEFAULT_TEXT());
    }

    private Update getUpdate(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%command%", replaced), Update.class);
    }
}