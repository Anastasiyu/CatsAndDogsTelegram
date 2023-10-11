package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.Volunteer;
import com.example.catsanddogstelegram.exception.UserNotFoundException;
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
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private VolunteerRepository volunteerRepository;
    @InjectMocks
    private VolunteerService volunteerService;

    @Test
    void createVolunteerTest() {
        Volunteer testVolunteer = new Volunteer();
        testVolunteer.setId(1);
        testVolunteer.setName("Петров Иван");
        testVolunteer.setChatId(167L);
        testVolunteer.setOnline(true);
        when(volunteerRepository.save(testVolunteer)).thenReturn(testVolunteer);

        Assertions.assertThat(volunteerService.createVolunteer(testVolunteer)).isEqualTo(testVolunteer);
    }

    @Test
    void readVolunteerByIdTest() {
        Volunteer testVolunteer = new Volunteer();
        testVolunteer.setId(1);
        testVolunteer.setName("Петров Иван");
        testVolunteer.setChatId(167L);
        testVolunteer.setOnline(true);
        when(volunteerRepository.findById(testVolunteer.getId())).thenReturn(Optional.of(testVolunteer));
        Assertions.assertThat(volunteerService.readVolunteerById(testVolunteer.getId())).isEqualTo(testVolunteer);
    }

    @Test
    void updateVolunteerTest() {
        Volunteer testVolunteer = new Volunteer();
        testVolunteer.setId(1);
        testVolunteer.setName("Петров Иван");
        testVolunteer.setChatId(167L);
        testVolunteer.setOnline(true);

        when(volunteerRepository.findById(testVolunteer.getId())).thenReturn(Optional.of(testVolunteer));
        when(volunteerRepository.save(testVolunteer)).thenReturn(testVolunteer);

        Assertions.assertThat(volunteerService.updateVolunteer(testVolunteer)).isEqualTo(testVolunteer);
    }

    @Test
    void deleteVolunteerTest() {
        Volunteer testVolunteer = new Volunteer();
        testVolunteer.setId(1);
        testVolunteer.setName("Петров Иван");
        testVolunteer.setChatId(167L);
        testVolunteer.setOnline(true);

        when(volunteerRepository.findById(testVolunteer.getId())).thenReturn(Optional.of(testVolunteer));
        volunteerService.deleteVolunteer(testVolunteer.getId());
        ArgumentCaptor<Volunteer> argumentCaptor = ArgumentCaptor.forClass(Volunteer.class);
        verify(volunteerRepository).delete(argumentCaptor.capture());
        Volunteer actual = argumentCaptor.getValue();

        Assertions.assertThat(actual).isEqualTo(testVolunteer);
    }


    @Test
    void onCommandReceivedTest() {
        Volunteer testVolunteer = new Volunteer();
        testVolunteer.setId(1);
        testVolunteer.setName("Петров Иван");
        testVolunteer.setChatId(167L);
        testVolunteer.setOnline(false);
        when(volunteerRepository.findByChatId(testVolunteer.getChatId())).thenReturn(Optional.of(testVolunteer));

        volunteerService.onCommandReceived(testVolunteer.getChatId());
        ArgumentCaptor<Volunteer> argumentCaptor = ArgumentCaptor.forClass(Volunteer.class);
        verify(volunteerRepository).save(argumentCaptor.capture());
        Volunteer actual = argumentCaptor.getValue();

        Assertions.assertThat(actual).isEqualTo(testVolunteer);

    }

    @Test
    void offCommandReceivedTest() {
        Volunteer testVolunteer = new Volunteer();
        testVolunteer.setId(1);
        testVolunteer.setName("Петров Иван");
        testVolunteer.setChatId(167L);
        testVolunteer.setOnline(true);
        when(volunteerRepository.findByChatId(testVolunteer.getChatId())).thenReturn(Optional.of(testVolunteer));

        volunteerService.offCommandReceived(testVolunteer.getChatId());
        ArgumentCaptor<Volunteer> argumentCaptor = ArgumentCaptor.forClass(Volunteer.class);
        verify(volunteerRepository).save(argumentCaptor.capture());
        Volunteer actual = argumentCaptor.getValue();

        Assertions.assertThat(actual).isEqualTo(testVolunteer);
    }

    @Test
    void volunteerCommandReceivedTest() throws URISyntaxException, IOException {
        Volunteer testVolunteer = new Volunteer();
        testVolunteer.setId(1);
        testVolunteer.setName("Петров Иван");
        testVolunteer.setChatId(1673L);
        testVolunteer.setOnline(true);

        List<Long> list = new ArrayList<>();
        list.add(testVolunteer.getChatId());

        String json = Files.readString(
                Paths.get(Objects.requireNonNull(TelegramMessageServiceTest.class.getResource("/com.example.catsanddogstelegram.service/" +
                        "text_update_service.json")).toURI()));
        Update update = BotUtils.fromJson(json.replace("%command%", "/volunteer"), Update.class);

        when(volunteerRepository.findAllByOnline(true)).thenReturn(Collections.emptyList())
                .thenReturn(list);

        volunteerService.volunteerCommandReceived(update.message().from().id(), update.message().from().firstName());
        volunteerService.volunteerCommandReceived(update.message().from().id(), update.message().from().firstName());

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, times(3)).execute(argumentCaptor.capture());
        List<SendMessage> actual = argumentCaptor.getAllValues();

        Assertions.assertThat(actual.get(0).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(0).getParameters().get("text"))
                .isEqualTo("В доме пусто, попробуйте позже");

        Assertions.assertThat(actual.get(1).getParameters().get("chat_id")).isEqualTo(167L);
        Assertions.assertThat(actual.get(1).getParameters().get("text"))
                .isEqualTo("Помощь в пути...");

        Assertions.assertThat(actual.get(2).getParameters().get("chat_id")).isEqualTo(1673L);
        Assertions.assertThat(actual.get(2).getParameters().get("text"))
                .isEqualTo(update.message().from().firstName() + " просит помощи");
        Assertions.assertThat(actual.get(2).getParameters().get("reply_markup"))
                .isEqualTo(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("help").url("tg://user?id=" + 167L)));
    }

    @Test
    void UserNotFoundExceptionTest() {
        Volunteer testVolunteer = new Volunteer();
        testVolunteer.setId(1);
        testVolunteer.setName("Петров Иван");
        testVolunteer.setChatId(167L);
        testVolunteer.setOnline(true);
        when(volunteerRepository.findByChatId(testVolunteer.getChatId())).thenReturn(Optional.empty());

        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> volunteerService.offCommandReceived(testVolunteer.getChatId()));
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> volunteerService.onCommandReceived(testVolunteer.getChatId()));
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> volunteerService.readVolunteerById(testVolunteer.getId()));
    }
}