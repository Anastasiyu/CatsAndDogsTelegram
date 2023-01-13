package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.Cat;
import com.example.catsanddogstelegram.exception.CatNotFoundException;
import com.example.catsanddogstelegram.repository.CatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CatService {
    private final CatRepository catRepository;

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Cat create(Cat cat) {
        log.info("method create started");
        cat.setAnimalId(null);
        return catRepository.save(cat);
    }

    public Cat read(int animalId) {
        log.info("method read started");
        return catRepository.findById(animalId).orElseThrow(() -> new CatNotFoundException(animalId));
    }

    public Cat update(int animalId, Cat cat) {
        log.info("method update started");
        return catRepository.save(cat);
    }

    public void delete(int animalId) {
        log.info("method delete started");
        catRepository.deleteById(animalId);
    }
}
