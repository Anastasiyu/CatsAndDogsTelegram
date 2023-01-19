package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.record.CatAdopterRecord;
import com.example.catsanddogstelegram.service.CatAdopterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cat/adopter")
public class CatAdopterController {
    private final CatAdopterService adopterService;

    public CatAdopterController(CatAdopterService adopterService) {
        this.adopterService = adopterService;
    }

    @Operation(summary = "Занесение в базу данных информации о новом усыновителе",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "информации об усыновителе, занесенная в базу",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CatAdopterRecord.class)
                            )
                    )
            }, tags = "CatAdopter"
    )
    @PostMapping
    public ResponseEntity<CatAdopterRecord> createCatAdopter(@RequestBody CatAdopterRecord record){
        return ResponseEntity.status(HttpStatus.CREATED).body(adopterService.createCatAdopter(record));
    }

    @Operation(summary = "Поиск данных об усыновителе по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация об усыновителе, найденная по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CatAdopterRecord.class)
                            )
                    )
            }, tags = "CatAdopter"
    )
    @GetMapping("{id}")
    public ResponseEntity<CatAdopterRecord> readCatAdopter(@PathVariable(name = "id") long chatId){
        return ResponseEntity.ok(adopterService.readCatAdopter(chatId));
    }

    @Operation(summary = "Изменение данных об усыновителе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новые данные об усыновителе",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CatAdopterRecord.class)
                            )
                    )
            }, tags = "CatAdopter"
    )
    @PutMapping
    public ResponseEntity<CatAdopterRecord> updateCatAdopter(@RequestBody CatAdopterRecord record){
        return ResponseEntity.ok(adopterService.updateCatAdopter(record));
    }

    @Operation(summary = "Установление статуса прохождения испытательного срока",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новые данные об усыновителе",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CatAdopterRecord.class)
                            )
                    )
            }, tags = "CatAdopter"
    )
    @PutMapping("{id}/pass")
    public ResponseEntity<CatAdopterRecord> setPassStatus(@PathVariable(name = "id") long chatId,
                                                          @RequestParam boolean pass){
        adopterService.setPass(pass, chatId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Добавление дней испытательного срока по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новые данные об усыновителе",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CatAdopterRecord.class)
                            )
                    )
            }, tags = "CatAdopter"
    )
    @PutMapping("{id}/add")
    public ResponseEntity<CatAdopterRecord> addDaysToEnd(@PathVariable(name = "id") long chatId,
                                                         @RequestParam int days){
        adopterService.addDays(chatId, days);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удаление данных об усыновителе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленные данные об усыновителе",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CatAdopterRecord.class)
                            )
                    )
            }, tags = "CatAdopter"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<CatAdopterRecord> deleteCatAdopter(@PathVariable(name = "id") long chatId){
        adopterService.deleteCatAdopter(chatId);
        return ResponseEntity.ok().build();
    }
}
