package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.record.DogAdopterRecord;
import com.example.catsanddogstelegram.service.DogAdopterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dog/adopter")
public class DogAdopterController {
    private final DogAdopterService adopterService;

    public DogAdopterController(DogAdopterService adopterService) {
        this.adopterService = adopterService;
    }

    @Operation(summary = "Занесение в базу данных информации о новом усыновителе",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "информации об усыновителе, занесенная в базу",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DogAdopterRecord.class)
                            )
                    )
            }, tags = "DogAdopter"
    )
    @PostMapping
    public ResponseEntity<DogAdopterRecord> createDogAdopter(@RequestBody DogAdopterRecord record){
        return ResponseEntity.status(HttpStatus.CREATED).body(adopterService.createDogAdopter(record));
    }

    @Operation(summary = "Поиск данных об усыновителе по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация об усыновителе, найденная по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DogAdopterRecord.class)
                            )
                    )
            }, tags = "DogAdopter"
    )
    @GetMapping("{id}")
    public ResponseEntity<DogAdopterRecord> readDogAdopter(@PathVariable(name = "id") long chatId){
        return ResponseEntity.ok(adopterService.readDogAdopter(chatId));
    }

    @Operation(summary = "Изменение данных об усыновителе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новые данные об усыновителе",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DogAdopterRecord.class)
                            )
                    )
            }, tags = "DogAdopter"
    )
    @PutMapping
    public ResponseEntity<DogAdopterRecord> updateDogAdopter(@RequestBody DogAdopterRecord record){
        return ResponseEntity.ok(adopterService.updateDogAdopter(record));
    }

    @Operation(summary = "Установление статуса прохождения испытательного срока",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новые данные об усыновителе",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DogAdopterRecord.class)
                            )
                    )
            }, tags = "DogAdopter"
    )
    @PutMapping("{id}/pass")
    public ResponseEntity<DogAdopterRecord> setPassStatus(@PathVariable(name = "id") long chatId,
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
                                    schema = @Schema(implementation = DogAdopterRecord.class)
                            )
                    )
            }, tags = "DogAdopter"
    )
    @GetMapping("{id}/add")
    public ResponseEntity<DogAdopterRecord> addDaysToEnd(@PathVariable(name = "id") long chatId,
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
                                    schema = @Schema(implementation = DogAdopterRecord.class)
                            )
                    )
            }, tags = "DogAdopter"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<DogAdopterRecord> deleteDogAdopter(@PathVariable(name = "id") long chatId){
        adopterService.deleteDogAdopter(chatId);
        return ResponseEntity.ok().build();
    }
}
