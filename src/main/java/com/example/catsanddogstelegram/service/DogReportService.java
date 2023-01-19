package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.DogReport;
import com.example.catsanddogstelegram.repository.DogReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogReportService {
    private final DogReportRepository dogReportRepository;

    public DogReportService(DogReportRepository dogReportRepository) {
        this.dogReportRepository = dogReportRepository;
    }

    public DogReport create(DogReport report){
        DogReport dogReport = dogReportRepository.findByFilePath(report.getFilePath()).orElse(null);
        if(dogReport == null){
            return dogReportRepository.save(report);
        }else{
            dogReport.setText(report.getText());
            return dogReportRepository.save(dogReport);
        }
    }

    public List<DogReport> readAllByDay(String date){
        return dogReportRepository.findAllByFilePathContains(date);
    }

    public void clear(long chatId){
        dogReportRepository.deleteAllByChatId(chatId);
    }
}
