package com.example.catsanddogstelegram.repository;

import com.example.catsanddogstelegram.entity.CatReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CatReportRepository extends JpaRepository<CatReport, Long> {
    Optional<CatReport> findByFilePath(String filePath);

    List<CatReport> findAllByFilePathContains(String date);

    void deleteAllByChatId(long chatId);
}
