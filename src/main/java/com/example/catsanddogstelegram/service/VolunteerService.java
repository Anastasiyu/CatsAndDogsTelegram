package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.Volunteer;
import com.example.catsanddogstelegram.exception.UserNotFoundException;
import com.example.catsanddogstelegram.repository.VolunteerRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@Slf4j
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final TelegramBot telegramBot;

    //CRUD

    public void volunteerCommandReceived(long chatId, String name) {
        log.debug("method volunteerCommandReceived started");
        List<Long> list = volunteerRepository.findAllByOnline(true);
        if(!list.isEmpty()){
            sendMessage(chatId, "Помощь в пути...");
            list.forEach(id -> {
                SendMessage request = new SendMessage(id, name + " просит помощи")
                        .replyMarkup(new InlineKeyboardMarkup(
                                new InlineKeyboardButton("help").url("tg://user?id=" + chatId)
                        ));
                telegramBot.execute(request);
            });
        } else{
            sendMessage(chatId, "В доме пусто, попробуйте позже");
        }
    }

    public void onCommandReceived(long chatId) {
        log.debug("method volunteerCommandReceived started");
        Volunteer volunteer = volunteerRepository.findByChatId(chatId).orElseThrow(UserNotFoundException::new);
        volunteer.setOnline(true);
        volunteerRepository.save(volunteer);
    }

    public void offCommandReceived(long chatId) {
        log.debug("method volunteerCommandReceived started");
        Volunteer volunteer = volunteerRepository.findByChatId(chatId).orElseThrow(UserNotFoundException::new);
        volunteer.setOnline(false);
        volunteerRepository.save(volunteer);
    }

    private void sendMessage(long chatId, String textToSend) {
        log.debug("method sendMessage started");
        SendMessage message = new SendMessage(chatId, textToSend);
        telegramBot.execute(message);
    }
}
