package com.example.catsanddogstelegram.repository;

import com.example.catsanddogstelegram.entity.CatAdopter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional
public interface CatAdoptersRepository extends JpaRepository<CatAdopter, Long> {
    @Query(nativeQuery = true, value = "SELECT report_request FROM cat_adopters WHERE chat_id = :id")
    boolean getRequestStatus(@Param(value = "id") long chatId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE cat_adopters SET report_request = :status WHERE chat_id = :id")
    void setStatus(@Param(value = "id") long chatId,
                   @Param(value = "status") boolean status);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE cat_adopters SET report_request = :status, last_report = :time WHERE chat_id = :id")
    void setStatus(@Param(value = "id") long chatId,
                   @Param(value = "status") boolean status,
                   @Param(value = "time") Timestamp time);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE cat_adopters SET end_day = end_day + :days WHERE chat_id = :id")
    void addDays(@Param(value = "id") long chatId,
                 @Param(value = "days") int days);

    @Query(nativeQuery = true, value = "SELECT chat_id FROM cat_adopters WHERE last_report < current_date AND pass IS NULL")
    List<Long> findAllExpired();

    @Query(nativeQuery = true, value = "Select chat_id from cat_adopters where (current_date - last_report) > 1 AND pass IS NULL")
    List<Long> findAllExpiredTooMuch();

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE cat_adopters SET pass = :pass WHERE chat_id = :id")
    void setPass(@Param(value = "pass") boolean pass,
                 @Param(value = "id") long id);
}
