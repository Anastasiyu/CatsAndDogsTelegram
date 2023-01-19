package com.example.catsanddogstelegram.repository;

import com.example.catsanddogstelegram.entity.DogReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DogReportRepository extends JpaRepository<DogReport, Long> {

    Optional<DogReport> findByFilePath(String filePath);

    List<DogReport> findAllByFilePathContains(String date);

    void deleteAllByChatId(long chatId);
}
