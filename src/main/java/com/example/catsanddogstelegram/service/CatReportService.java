package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.CatReport;
import com.example.catsanddogstelegram.repository.CatReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatReportService {
    private final CatReportRepository catReportRepository;

    public CatReportService(CatReportRepository catReportRepository) {
        this.catReportRepository = catReportRepository;
    }

    public CatReport create(CatReport report){
        CatReport catReport = catReportRepository.findByFilePath(report.getFilePath()).orElse(null);
        if(catReport == null){
            return catReportRepository.save(report);
        }else{
            catReport.setText(report.getText());
            return catReportRepository.save(catReport);
        }
    }

    public List<CatReport> readAllByDay(String date){
        return catReportRepository.findAllByFilePathContains(date);
    }

    public void clear(long chatId){
        catReportRepository.deleteAllByChatId(chatId);
    }
}
