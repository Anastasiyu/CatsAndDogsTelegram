package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.User;
import com.example.catsanddogstelegram.exception.UserNotFoundException;
import com.example.catsanddogstelegram.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Сохранение в БД нового user при отправки им команды /start
     * @param chatId - идентификатор чата из которого пришел update
     * @param time - время первого сообщения пользователя
     * @param name - имя пользователя
     * @return сохраненный в БД user
     */
    public User createUser(long chatId, Timestamp time, String name){
        log.debug("method createUser started");
        User user = userRepository.findById(chatId).orElse(null);
        if(user == null) {
            user = new User();
            user.setChatId(chatId);
            user.setUserTime(time);
            user.setUserName(name);
            userRepository.save(user);
        }
        return user;
    }

    public User readUser(long chatId){
        log.debug("method createUser started");
        return userRepository.findById(chatId).orElse(null);
    }

    /**
     * Изменение выбранного пользователем номера приюта
     * @param chatId - идентификатор чата из которого пришел update
     * @param type - номер приюта
     * @throws UserNotFoundException если пользователь не найден
     * @return измененный номер приюта
     */
    @CachePut(value = "shelter", key = "#chatId")
    public Integer setUser(long chatId, Integer type){
        log.debug("method setUser started");
        User user = userRepository.findById(chatId).orElseThrow(UserNotFoundException::new);
        user.setType(type);
        userRepository.save(user);
        return type;
    }

    /**
     * Сохранение контакта пользователя
     * @param chatId - идентификатор чата из которого пришел update
     * @param number - сообщение пользователя с контактом
     * @throws UserNotFoundException если пользователь не найден
     */
    public void setUser(long chatId, String number){
        log.debug("method setUser started");
        User user = userRepository.findById(chatId).orElseThrow(UserNotFoundException::new);
        user.setPhoneNumber(number);
        userRepository.save(user);
    }

    /**
     * Изменение статуса запроса в БД
     * @param chatId - идентификатор чата из которого пришел update
     * @param status - статус запроса для изменения
     * @throws UserNotFoundException если пользователь не найден
     * @return измененный статус
     */
    @CachePut(value = "request", key = "#chatId")
    public boolean setUser(long chatId, boolean status){
        log.debug("method setUser started");
        User user = userRepository.findById(chatId).orElseThrow(UserNotFoundException::new);
        user.setStatus(status);
        userRepository.save(user);
        log.info("saved " + user.isStatus() + " for " + user);
        return status;
    }

    /**
     * Запрос номера приюта пользователя из БД
     * @param chatId - идентификатор чата из которого пришел update
     * @throws UserNotFoundException если пользователь не найден
     * @return выбранный пользователем номер приюта
     */
    @Cacheable("shelter")
    public int getShelterType(long chatId) {
        log.debug("method getShelterType started");
        return userRepository.findShelterTypeByChatId(chatId).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Запрос статуса запроса пользователя из БД
     * @param chatId - идентификатор чата из которого пришел update
     * @throws UserNotFoundException если пользователь не найден
     * @return статус запроса пользователя
     */
    @Cacheable("request")
    public boolean getRequestStatus(long chatId) {
        log.debug("method getShelterType started");
        return userRepository.findRequestStatus(chatId).orElseThrow(UserNotFoundException::new);
    }
}
