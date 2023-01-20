package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.CatReport;
import com.example.catsanddogstelegram.exception.ReportNotFoundException;
import com.example.catsanddogstelegram.repository.CatReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Slf4j
public class CatReportService {
    private final CatReportRepository catReportRepository;

    public CatReportService(CatReportRepository catReportRepository) {
        this.catReportRepository = catReportRepository;
    }

    /**
     * Сохранение информации об отчете в БД
     * @param report объект с информацией об отчете
     * @return сохраненный отчет
     */
    public CatReport create(CatReport report){
        log.debug("was invoking method create");
        CatReport catReport = catReportRepository.findByFilePath(report.getFilePath()).orElse(null);
        if(catReport == null){
            return catReportRepository.save(report);
        }else{
            catReport.setText(report.getText());
            return catReportRepository.save(catReport);
        }
    }

    /**
     * Поиск отчетов в БД присланный в определённый день
     * @param date дата для поиска
     * @return список отчетов
     */
    public List<CatReport> readAllByDay(String date){
        log.debug("was invoking method readAllByDay");
        return catReportRepository.findAllByFilePathContains(date);
    }

    /**
     * Удаление отчетов определенного усыновителя
     * @param chatId id усыновителя
     */
    public void clear(long chatId){
        log.debug("was invoking method clear");
        catReportRepository.deleteAllByChatId(chatId);
    }

    /**
     * Чтение файла изображения по пути из отчета в БД
     * @param id id отчета
     * @return массив байт изображения
     * @throws IOException - не найден файл
     */
    public byte[] readFromFile(long id) throws IOException {
        log.debug("was invoking method readFromFile");
        CatReport report = catReportRepository.findById(id).orElseThrow(() -> {
            log.error("There is not report with id = " + id);
            throw new ReportNotFoundException();
        });
        Path path = Path.of(report.getFilePath());
        return Files.readAllBytes(path);
    }
}
