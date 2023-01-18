package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.CatReport;
import com.example.catsanddogstelegram.entity.DogReport;
import com.example.catsanddogstelegram.repository.CatReportRepository;
import com.example.catsanddogstelegram.repository.DogReportRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Service
@Slf4j
public class ReportMessageService {
    private final TelegramBot telegramBot;
    private final UserService userService;
    private final CatAdopterService catAdopterService;
    private final DogAdopterService dogAdopterService;
    private final DogReportRepository dogReportRepository;
    private final CatReportRepository catReportRepository;

    @Value("${path.to.dog.folder}")
    private String dogDir;
    @Value("${path.to.cat.folder}")
    private String catDir;

    public ReportMessageService(TelegramBot telegramBot, UserService userService, CatAdopterService catAdopterService, DogAdopterService dogAdopterService, DogReportRepository dogReportRepository, CatReportRepository catReportRepository) {
        this.telegramBot = telegramBot;
        this.userService = userService;
        this.catAdopterService = catAdopterService;
        this.dogAdopterService = dogAdopterService;
        this.dogReportRepository = dogReportRepository;
        this.catReportRepository = catReportRepository;
    }

    public void reportUpdateListener(Update update, long chatId){
        if(update.message().photo() == null || update.message().caption() ==null){
            SendMessage smg = new SendMessage(chatId, "Отправьте фотографию с прикрепленным к ней текстом")
                    .replyMarkup(new InlineKeyboardMarkup(
                            new InlineKeyboardButton("отмена")
                                    .callbackData("report cancel")));
            telegramBot.execute(smg);
            return;
        }
        if(userService.getShelterType(chatId) == 1){
            DogReport report = new DogReport();
            report.setChatId(chatId);
            report.setFilePath(savePhoto(update.message().photo()[2].fileId(), dogDir, chatId));
            report.setText(update.message().caption());
            dogReportRepository.save(report);
            dogAdopterService.setStatus(chatId, false);
        } else {
            CatReport report = new CatReport();
            report.setChatId(chatId);
            report.setFilePath(savePhoto(update.message().photo()[2].fileId(), catDir, chatId));
            report.setText(update.message().caption());
            catReportRepository.save(report);
            catAdopterService.setStatus(chatId, false);
        }
        SendMessage smg = new SendMessage(chatId, "На сегодня все");
        telegramBot.execute(smg);
    }

    public String savePhoto(String fileId, String dir, long chatId){
        GetFile getFile = new GetFile(fileId);
        String url = telegramBot.getFullFilePath(telegramBot.execute(getFile).file());
        Path path = Path.of(dir,
                chatId + "_" + LocalDateTime.now().getDayOfMonth() + ".jpg");
        try (InputStream is = new URL(url).openStream()){
            byte[] img = is.readAllBytes();
            Files.createDirectories(path.getParent());
            Files.write(path, img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path.toString();
    }

    public void cancelCommandReceived(long chatId) {
        if(userService.getShelterType(chatId) == 1){
            dogAdopterService.setStatus(chatId, false);
        } else {
            catAdopterService.setStatus(chatId, false);
        }
        SendMessage smg = new SendMessage(chatId, "В другой раз");
        telegramBot.execute(smg);
    }
}
