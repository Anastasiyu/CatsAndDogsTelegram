package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.repository.VolunteerRepository;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ReportMessageServiceTest {
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private UserService userService;
    @Mock
    private CatAdopterService catAdopterService;
    @Mock
    private DogAdopterService dogAdopterService;
    @Mock
    private DogReportService dogReportService;
    @Mock
    private CatReportService catReportService;
    @Mock
    private VolunteerRepository volunteerRepository;
    @InjectMocks
    private ReportMessageService reportMessageService;

    @Test
    void reportUpdateListener() {
    }

    @Test
    void savePhoto() {
    }

    @Test
    void cancelCommandReceived() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = BotUtils.fromJson(json.replace("%command%", "report cancel"), Update.class);
        reportMessageService.cancelCommandReceived(update.message().from().id());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("В другой раз");
    }

    @Test
    void sendDefaultMessage() {
        reportMessageService.sendDefaultMessage(12345L);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(12345L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно," +
                        " как необходимо. Пожалуйста, подойди ответственнее к этому занятию. В противном случае" +
                        " волонтеры приюта будут обязаны самолично проверять условия содержания животного");
    }

    @Test
    void checker() {
        List<Long> expired1 = new ArrayList<>();
        expired1.add(123L);
        List<Long> expired2 = new ArrayList<>();
        expired2.add(123L);
        List<Long> volunteer = List.of(133L);

        when(dogAdopterService.findAllExpired()).thenReturn(expired1);
        when(catAdopterService.findAllExpired()).thenReturn(expired1);
        when(dogAdopterService.findAllExpiredTooMuch()).thenReturn(expired2);
        when(catAdopterService.findAllExpiredTooMuch()).thenReturn(Collections.emptyList());
        when(volunteerRepository.findAllByOnline(true)).thenReturn(volunteer);

        reportMessageService.checker();

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, times(3)).execute(argumentCaptor.capture());
        List<SendMessage> actual = argumentCaptor.getAllValues();

        Assertions.assertThat(actual.get(0).getParameters().get("chat_id")).isEqualTo(123L);
        Assertions.assertThat(actual.get(0).getParameters().get("text"))
                .isEqualTo("Отправьте отчет как можно скорее");

        Assertions.assertThat(actual.get(1).getParameters().get("chat_id")).isEqualTo(123L);
        Assertions.assertThat(actual.get(1).getParameters().get("text"))
                .isEqualTo("Отправьте отчет как можно скорее");

        Assertions.assertThat(actual.get(2).getParameters().get("chat_id")).isEqualTo(133L);
        Assertions.assertThat(actual.get(2).getParameters().get("text"))
                .isEqualTo("Нет отчета более 2 дней");
        Assertions.assertThat(actual.get(2).getParameters().get("reply_markup"))
                .isEqualTo(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("help").url("tg://user?id=" + 123L)
                ));
    }
}