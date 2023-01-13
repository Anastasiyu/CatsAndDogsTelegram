package com.example.catsanddogstelegram.repository;

import com.example.catsanddogstelegram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUserByUserName(String userName);
    @Query(nativeQuery = true, value = "SELECT shelter_type FROM users WHERE chat_id = :id")
    int findShelterTypeByChatId(@Param(value = "id") long chatId);

    @Query(nativeQuery = true, value = "SELECT request_status FROM users WHERE chat_id = :id")
    boolean findRequestStatus(@Param(value = "id") long chatId);
}
