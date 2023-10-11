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

    /**
     * Сохранение новой сущности Cat в БД с заданными параметрами
     * @param dog - сущность для сохранения
     * @return сохраненная сущность
     */
    public Dog createDog(Dog dog) {
        log.debug("method create started");
        return dogRepository.save(dog);
    }

    /**
     * Поиск сущности в БД по id
     * @param animalId - идентификатор сущности в БД
     * @return найденная в БД сущность
     */
    public Dog readDog(int animalId) {
        log.debug("method read started");
        return dogRepository.findById(animalId).orElseThrow(() -> new DogNotFoundException(animalId));
    }

    /**
     * Изменение существующей в БД сущности
     * @param dog - сущность с изменениями
     * @return измененная сущность
     */
    public Dog updateDog(Dog dog) {
        log.debug("method updateDog started");
        Dog toUpdate = readDog(dog.getAnimalId());
        toUpdate.setIsMale(dog.getIsMale());
        toUpdate.setDescription(dog.getDescription());
        toUpdate.setAnimalAge(dog.getAnimalAge());
        toUpdate.setAnimalName(dog.getAnimalName());
        return dogRepository.save(toUpdate);
    }

    /**
     * Удаление сущности из БД по id
     * @param animalId - идентификатор сущности в БД
     */
    public void deleteDog(int animalId) {
        log.debug("method delete started");
        dogRepository.delete(readDog(animalId));
    }
}


