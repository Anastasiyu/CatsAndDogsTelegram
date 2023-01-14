package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.constants.PreparingInfoCat;
import com.example.catsanddogstelegram.constants.PreparingInfoDog;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdoptMessageServiceTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdoptMessageService adoptMessageService;

    @Test
    void meetCommandReceivedDogTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json);
        when(userService.getShelterType(update.message().from().id())).thenReturn(1);
        adoptMessageService.meetCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(PreparingInfoDog.DOG_RULES_OF_DATING.getTypeOfInfo());
    }

    @Test
    void meetCommandReceivedCatTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json);
        when(userService.getShelterType(update.message().from().id())).thenReturn(2);
        adoptMessageService.meetCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(PreparingInfoCat.CAT_RULES_OF_DATING.getTypeOfInfo());
    }

    @Test
    void docsCommandReceivedDogTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json);
        adoptMessageService.docsCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(PreparingInfoDog.LIST_OF_DOCUMENTS_TO_ADOPT.getTypeOfInfo());
    }

    @Test
    void transportCommandReceivedDogTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json);
        when(userService.getShelterType(update.message().from().id())).thenReturn(1);
        adoptMessageService.transportCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(PreparingInfoDog.TRANSPORTATION_ADVICE.getTypeOfInfo());

    }

    @Test
    void transportCommandReceivedCatTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json);
        when(userService.getShelterType(update.message().from().id())).thenReturn(2);
        adoptMessageService.transportCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(PreparingInfoCat.TRANSPORTATION_ADVICE.getTypeOfInfo());

    }

    @Test
    void preparingCommandReceivedDogTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json);
        when(userService.getShelterType(update.message().from().id())).thenReturn(1);
        adoptMessageService.preparingCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot, atLeast(3)).execute(argumentCaptor.capture());
        List<SendMessage> actual = argumentCaptor.getAllValues();

        Assertions.assertThat(actual.get(0).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(0).getParameters().get("text"))
                .isEqualTo(PreparingInfoDog.PREPARING_HOUSE_FOR_A_PUPPY_DOG.getTypeOfInfo());

        Assertions.assertThat(actual.get(1).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(1).getParameters().get("text"))
                .isEqualTo(PreparingInfoDog.PREPARING_HOUSE_FOR_AN_ADULT_DOG.getTypeOfInfo());

        Assertions.assertThat(actual.get(2).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(2).getParameters().get("text"))
                .isEqualTo(PreparingInfoDog.PREPARING_HOUSE_FOR_A_DISABLED_DOG.getTypeOfInfo());
    }

    @Test
    void preparingCommandReceivedCatTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json);
        when(userService.getShelterType(update.message().from().id())).thenReturn(2);
        adoptMessageService.preparingCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot, atLeast(3)).execute(argumentCaptor.capture());
        List<SendMessage> actual = argumentCaptor.getAllValues();

        Assertions.assertThat(actual.get(0).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(0).getParameters().get("text"))
                .isEqualTo(PreparingInfoCat.PREPARING_HOUSE_FOR_A_KITTY.getTypeOfInfo());

        Assertions.assertThat(actual.get(1).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(1).getParameters().get("text"))
                .isEqualTo(PreparingInfoCat.PREPARING_HOUSE_FOR_AN_ADULT_CAT.getTypeOfInfo());

        Assertions.assertThat(actual.get(2).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(2).getParameters().get("text"))
                .isEqualTo(PreparingInfoCat.PREPARING_HOUSE_FOR_A_DISABLED_CAT.getTypeOfInfo());
    }

    @Test
    void cynologistCommandReceivedDogTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json);
        when(userService.getShelterType(update.message().from().id())).thenReturn(1);
        adoptMessageService.cynologistCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot, atLeast(2)).execute(argumentCaptor.capture());
        List<SendMessage> actual = argumentCaptor.getAllValues();

        Assertions.assertThat(actual.get(0).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(0).getParameters().get("text"))
                .isEqualTo(PreparingInfoDog.DOG_HANDLERS_ADVICE.getTypeOfInfo());

        Assertions.assertThat(actual.get(1).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(1).getParameters().get("text"))
                .isEqualTo(PreparingInfoDog.VERIFIED_DOG_HANDLERS.getTypeOfInfo());
    }

    @Test
    void cynologistCommandReceivedFalseTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json);
        when(userService.getShelterType(update.message().from().id())).thenReturn(2);
        adoptMessageService.cynologistCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("Извините, данная команда не поддерживается!");
    }

    @Test
    void refuseCommandReceivedDogTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json);
        when(userService.getShelterType(update.message().from().id())).thenReturn(1);
        adoptMessageService.refuseCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();


        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(PreparingInfoDog.REASONS_FOR_REFUSAL.getTypeOfInfo());
    }

    @Test
    void refuseCommandReceivedCatTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = getUpdate(json);
        when(userService.getShelterType(update.message().from().id())).thenReturn(2);
        adoptMessageService.refuseCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(PreparingInfoCat.REASONS_FOR_REFUSAL.getTypeOfInfo());
    }

    private Update getUpdate(String json) {
        return BotUtils.fromJson(json, Update.class);
    }
}