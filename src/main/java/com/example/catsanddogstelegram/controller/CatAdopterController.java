package com.example.catsanddogstelegram.controller;

import com.example.catsanddogstelegram.record.CatAdopterRecord;
import com.example.catsanddogstelegram.service.CatAdopterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cat/adopter")
public class CatAdopterController {
    private final CatAdopterService adopterService;

    public CatAdopterController(CatAdopterService adopterService) {
        this.adopterService = adopterService;
    }

    @PostMapping
    public ResponseEntity<CatAdopterRecord> createCatAdopter(@RequestBody CatAdopterRecord record){
        return ResponseEntity.status(HttpStatus.CREATED).body(adopterService.createCatAdopter(record));
    }

    @GetMapping("{id}")
    public ResponseEntity<CatAdopterRecord> readCatAdopter(@PathVariable(name = "id") long chatId){
        return ResponseEntity.ok(adopterService.readCatAdopter(chatId));
    }

    @PutMapping
    public ResponseEntity<CatAdopterRecord> updateCatAdopter(@RequestBody CatAdopterRecord record){
        return ResponseEntity.ok(adopterService.updateCatAdopter(record));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CatAdopterRecord> deleteCatAdopter(@PathVariable(name = "id") long chatId){
        adopterService.deleteCatAdopter(chatId);
        return ResponseEntity.ok().build();
    }
}
