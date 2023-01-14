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

    /**
     * Сохранение волонтера с пришедшими параметрами в БД
     * @param volunteer - сущность, которая будет сохранена в БД
     * @return созданный волонтер
     */
    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    /**
     * Поиск волонтера в БД по id
     * @throws UserNotFoundException если волонтер не найден
     * @param id - идентификатор волонтера в БД
     * @return найденый в БД волонтер
     */
    public Volunteer readVolunteerById(int id) {
        return volunteerRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Изменение существующего волонтера в БД по id
     * @throws UserNotFoundException если волонтер не найден
     * @param volunteer - сущность с изменениями
     * @return измененный волонтер
     */
    public Volunteer updateVolunteer(Volunteer volunteer) {
        Volunteer toUpdate = readVolunteerById(volunteer.getId());
        toUpdate.setName(volunteer.getName());
        toUpdate.setChatId(volunteer.getChatId());
        toUpdate.setOnline(volunteer.isOnline());
        return volunteerRepository.save(toUpdate);
    }

    /**
     * Удаление волонтера из БД по id, если такой существует
     * @throws UserNotFoundException если волонтер не найден
     * @param id - идентификатор волонтера в БД
     */
    public void deleteVolunteer(int id) {
        volunteerRepository.delete(readVolunteerById(id));
    }

    /**
     * Обработка команды вызова волонтера пользователем.
     * Отправление кнопки с ссылкой на пользователя, всем волонтером со статусом isOnline = true
     * Поиск волонтеров в БД через {@link VolunteerRepository#findAllByOnline(boolean)}
     * @param chatId - идентификатор чата из которого пришел update
     * @param name - username пользователя
     */
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

    /**
     * Изменение статуса Online волонтера в БД на true
     * @throws UserNotFoundException если волонтер не найден
     * @param chatId - идентификатор чата из которого пришел update
     */
    public void onCommandReceived(long chatId) {
        log.debug("method volunteerCommandReceived started");
        Volunteer volunteer = volunteerRepository.findByChatId(chatId).orElseThrow(UserNotFoundException::new);
        volunteer.setOnline(true);
        volunteerRepository.save(volunteer);
    }

    /**
     * Изменение статуса Online волонтера в БД на false
     * @throws UserNotFoundException если волонтер не найден
     * @param chatId - идентификатор чата из которого пришел update
     */
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
