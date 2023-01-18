package com.example.catsanddogstelegram.repository;

import com.example.catsanddogstelegram.entity.DogAdopter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface DogAdoptersRepository extends JpaRepository<DogAdopter, Long> {
    @Query(nativeQuery = true, value = "SELECT report_request FROM dog_adopters WHERE chat_id = :id")
    boolean getRequestStatus(@Param(value = "id") long chatId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE dog_adopters SET report_request = :status WHERE chat_id = :id")
    void setStatus(@Param(value = "id") long chatId,
                   @Param(value = "status") boolean status);
}
