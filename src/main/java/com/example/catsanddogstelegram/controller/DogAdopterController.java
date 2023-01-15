package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.record.DogAdopterRecord;
import com.example.catsanddogstelegram.service.DogAdopterService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dog/adopter")
@Data
public class DogAdopterController {
    private final DogAdopterService adopterService;

    @PostMapping
    public ResponseEntity<DogAdopterRecord> createDogAdopter(@RequestBody DogAdopterRecord record){
        return ResponseEntity.status(HttpStatus.CREATED).body(adopterService.createDogAdopter(record));
    }

    @GetMapping("{id}")
    public ResponseEntity<DogAdopterRecord> readDogAdopter(@PathVariable(name = "id") long chatId){
        return ResponseEntity.ok(adopterService.readDogAdopter(chatId));
    }

    @PutMapping
    public ResponseEntity<DogAdopterRecord> updateDogAdopter(@RequestBody DogAdopterRecord record){
        return ResponseEntity.ok(adopterService.updateDogAdopter(record));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DogAdopterRecord> deleteDogAdopter(@PathVariable(name = "id") long chatId){
        adopterService.deleteDogAdopter(chatId);
        return ResponseEntity.ok().build();
    }
}
