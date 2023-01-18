package com.example.catsanddogstelegram.repository;

import com.example.catsanddogstelegram.entity.DogReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogReportRepository extends JpaRepository<DogReport, Long> {
}
