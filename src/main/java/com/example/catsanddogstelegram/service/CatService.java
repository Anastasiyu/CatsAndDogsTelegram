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

    /**
     * Сохранение новой сущности Cat в БД с заданными параметрами
     * @param cat - сущность для сохранения
     * @return сохраненная сущность
     */
    public Cat createCat(Cat cat) {
        log.debug("method createCat started");
        return catRepository.save(cat);
    }

    /**
     * Поиск сущности в БД по id
     * @param animalId - идентификатор сущности в БД
     * @return найденная в БД сущность
     */
    public Cat readCat(int animalId) {
        log.debug("method readCat started");
        return catRepository.findById(animalId).orElseThrow(() -> new CatNotFoundException(animalId));
    }

    /**
     * Изменение существующей в БД сущности
     * @param cat - сущность с изменениями
     * @return измененная сущность
     */
    public Cat updateCat(Cat cat) {
        log.debug("method updateCat started");
        Cat toUpdate = readCat(cat.getAnimalId());
        toUpdate.setIsMale(cat.getIsMale());
        toUpdate.setAdopted(cat.isAdopted());
        toUpdate.setDescription(cat.getDescription());
        toUpdate.setAnimalAge(cat.getAnimalAge());
        toUpdate.setAnimalName(cat.getAnimalName());
        return catRepository.save(toUpdate);
    }

    /**
     * Удаление сущности из БД по id
     * @param animalId - идентификатор сущности в БД
     */
    public void deleteCat(int animalId) {
        log.debug("method deleteCat started");
        catRepository.delete(readCat(animalId));
    }
}
