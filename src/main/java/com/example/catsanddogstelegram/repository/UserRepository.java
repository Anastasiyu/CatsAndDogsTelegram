package com.example.catsanddogstelegram.repository;


import com.example.catsanddogstelegram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Collection<User> findAllByChatId(Long chatId);}