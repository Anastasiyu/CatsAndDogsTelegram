package com.example.catsanddogstelegram.repository;

import com.example.catsanddogstelegram.entity.CatReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatReportRepository extends JpaRepository<CatReport, Long> {
}
