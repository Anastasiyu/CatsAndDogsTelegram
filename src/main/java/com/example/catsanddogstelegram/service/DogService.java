package com.example.catsanddogstelegram.service;

import com.example.catsanddogstelegram.entity.Dog;
import com.example.catsanddogstelegram.exception.DogNotFoundException;
import com.example.catsanddogstelegram.repository.DogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DogService {
    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public Dog create(Dog dog) {
        log.info("method create started");
        dog.setAnimalId(null);
        return dogRepository.save(dog);
    }

    public Dog read(int animalId) {
        log.info("method read started");
        return dogRepository.findById(animalId).orElseThrow(() -> new DogNotFoundException(animalId));
    }

    public Dog update(int animalId, Dog dog) {
        log.info("method update started");
        return dogRepository.save(dog);
    }

    public void delete(int animalId) {
        log.info("method delete started");
        dogRepository.deleteById(animalId);
    }
}


