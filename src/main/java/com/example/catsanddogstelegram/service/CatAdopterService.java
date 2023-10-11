package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.component.RecordMapper;
import com.example.catsanddogstelegram.entity.CatAdopter;
import com.example.catsanddogstelegram.exception.UserNotFoundException;
import com.example.catsanddogstelegram.record.CatAdopterRecord;
import com.example.catsanddogstelegram.repository.CatAdoptersRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CatAdopterService {
    private final CatAdoptersRepository adoptersRepository;
    private final TelegramBot telegramBot;
    private final RecordMapper mapper;

    public CatAdopterService(CatAdoptersRepository adoptersRepository, TelegramBot telegramBot, RecordMapper mapper) {
        this.adoptersRepository = adoptersRepository;
        this.telegramBot = telegramBot;
        this.mapper = mapper;
    }

    /**
     * Сохранение информации о новом усыновителе в БД
     * @param record - рекорд с информацией об усыновителе
     * @return сохраненный усыновитель
     */
    public CatAdopterRecord createCatAdopter(CatAdopterRecord record){
        CatAdopter catAdopter = mapper.toEntity(record);
        catAdopter.setRegisterTime(Timestamp.valueOf(LocalDateTime.now()));
        return mapper.toRecord(adoptersRepository.save(catAdopter));
    }

    /**
     * Поиск информации об усыновителе в БД
     * @param chatId id усыновителя
     * @return найденый усыновитель
     */
    public CatAdopterRecord readCatAdopter(long chatId){
        return mapper.toRecord(adoptersRepository.findById(chatId).orElseThrow(UserNotFoundException::new));
    }

    /**
     * Изменение информации об усыновителе в БД
     * @param adopter рекорд с информацией об усыновителе
     * @return сохраненный усыновитель
     */
    public CatAdopterRecord updateCatAdopter(CatAdopterRecord adopter){
        CatAdopter catAdopter = mapper.toEntity(readCatAdopter(adopter.getChatId()));
        catAdopter.setName(adopter.getName());
        catAdopter.setAge(adopter.getAge());
        catAdopter.setEmail(adopter.getEmail());
        catAdopter.setProneNumber(adopter.getProneNumber());
        catAdopter.setAddress(adopter.getAddress());
        return mapper.toRecord(adoptersRepository.save(catAdopter));
    }

    /**
     * Усновление статуса прохождения испытательного срока усыновителя
     * @param pass статус прохождения
     * @param id id усыновителя
     */
    public void setPass(boolean pass, long id) {
        adoptersRepository.setPass(pass, id);
        SendMessage msg;
        if(pass){
            msg = new SendMessage(id, "Поздравляем, вы прошли испытательный срок");
        }else{
            msg = new SendMessage(id, "Увы, вы не прошли испытательный срок");
        }
        telegramBot.execute(msg);
    }

    /**
     * Удаление информации об усыновителе из БД
     * @param chatId id усыновителя
     */
    public void deleteCatAdopter(long chatId){
        adoptersRepository.delete(mapper.toEntity(readCatAdopter(chatId)));
    }

    /**
     * Возврат статуса запроса отчета усыновителя
     * @param chatId id усыновителя
     * @return статуса запроса отчета
     */
    public boolean getRequestStatus(long chatId) {
        return adoptersRepository.getRequestStatus(chatId);
    }

    /**
     * Установление статуса запроса отчета
     * @param chatId id усыновителя
     * @param status статуса запроса отчета
     */
    public void setStatus(long chatId, boolean status) {
        adoptersRepository.setStatus(chatId, status);
    }

    /**
     * Установление статуса запроса отчета и времени последнего отчета
     * @param chatId id усыновителя
     * @param status статуса запроса отчета
     * @param time время последнего отчета
     */
    public void setStatus(long chatId, boolean status, Timestamp time) {
        adoptersRepository.setStatus(chatId, status, time);
    }

    /**
     * Продление испытательного срока для усыновителя по id
     * @param chatId id усыновителя
     * @param days количество дней
     */
    public void addDays(long chatId, int days) {
        adoptersRepository.addDays(chatId, days);
        SendMessage msg = new SendMessage(chatId, "Вам был продлен испытательный срок на " + days + " дней");
        telegramBot.execute(msg);
    }

    /**
     * Поиск в БД усыновителей, последний отчет которых был день назад
     * @return список id чата
     */
    public List<Long> findAllExpired() {
        return adoptersRepository.findAllExpired();
    }

    /**
     * Поиск в БД усыновителей, последний отчет которых был более одного дня назад
     * @return список id чата
     */
    public List<Long> findAllExpiredTooMuch() {
        return adoptersRepository.findAllExpiredTooMuch();
    }
}
