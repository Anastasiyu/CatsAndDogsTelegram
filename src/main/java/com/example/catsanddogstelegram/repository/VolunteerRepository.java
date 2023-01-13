package com.example.catsanddogstelegram.repository;

import com.example.catsanddogstelegram.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {
    @Query(nativeQuery = true, value = "SELECT chat_id FROM volunteers WHERE is_online = true")
    List<Long> findAllByOnline(boolean b);

    Optional<Volunteer> findByChatId(long chatId);
}
