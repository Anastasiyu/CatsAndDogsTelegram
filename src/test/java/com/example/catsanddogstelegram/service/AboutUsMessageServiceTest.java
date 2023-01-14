package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.constants.CatShelterDescription;
import com.example.catsanddogstelegram.constants.DogShelterDescription;
import com.example.catsanddogstelegram.constants.MenuTexts;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AboutUsMessageServiceTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private UserService userService;

    @InjectMocks
    private AboutUsMessageService aboutUsMessageService;


    @Test
    void infoCommandReceivedDogTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json);
        Mockito.when(userService.getShelterType(update.message().from().id())).thenReturn(1);
        aboutUsMessageService.infoCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(DogShelterDescription.INFO.getTypeOfInfoAboutShelter());
    }

    @Test
    void infoCommandReceivedCatTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json);
        Mockito.when(userService.getShelterType(update.message().from().id())).thenReturn(2);
        aboutUsMessageService.infoCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(CatShelterDescription.INFO.getTypeOfInfoAboutShelter());
    }

    @Test
    void timeCommandReceivedDogTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json);
        Mockito.when(userService.getShelterType(update.message().from().id())).thenReturn(1);
        aboutUsMessageService.timeCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(DogShelterDescription.OPENING_HOURS.getTypeOfInfoAboutShelter());
    }

    @Test
    void timeCommandReceivedCatTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json);
        Mockito.when(userService.getShelterType(update.message().from().id())).thenReturn(2);
        aboutUsMessageService.timeCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(CatShelterDescription.OPENING_HOURS.getTypeOfInfoAboutShelter());
    }

    @Test
    void addressCommandReceivedDogTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json);
        Mockito.when(userService.getShelterType(update.message().from().id())).thenReturn(1);
        aboutUsMessageService.addressCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(DogShelterDescription.ADDRESS.getTypeOfInfoAboutShelter());
    }

    @Test
    void addressCommandReceivedCatTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json);
        Mockito.when(userService.getShelterType(update.message().from().id())).thenReturn(2);
        aboutUsMessageService.addressCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(CatShelterDescription.ADDRESS.getTypeOfInfoAboutShelter());
    }

    @Test
    void contactsCommandReceivedDogTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json);
        Mockito.when(userService.getShelterType(update.message().from().id())).thenReturn(1);
        aboutUsMessageService.contactsCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(DogShelterDescription.CONTACTS.getTypeOfInfoAboutShelter());
    }

    @Test
    void contactsCommandReceivedCatTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json);
        Mockito.when(userService.getShelterType(update.message().from().id())).thenReturn(2);
        aboutUsMessageService.contactsCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(CatShelterDescription.CONTACTS.getTypeOfInfoAboutShelter());
    }

    @Test
    void safetyCommandReceivedDogTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json);
        Mockito.when(userService.getShelterType(update.message().from().id())).thenReturn(1);
        aboutUsMessageService.safetyCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(DogShelterDescription.SAFETY_MEASURES.getTypeOfInfoAboutShelter());
    }
    @Test
    void safetyCommandReceivedCatTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json").toURI()));
        Update update = getUpdate(json);
        Mockito.when(userService.getShelterType(update.message().from().id())).thenReturn(2);
        aboutUsMessageService.safetyCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(CatShelterDescription.SAFETY_MEASURES.getTypeOfInfoAboutShelter());
    }

    private Update getUpdate(String json) {
        return BotUtils.fromJson(json, Update.class);
    }

}