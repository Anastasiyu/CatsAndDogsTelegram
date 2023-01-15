package com.example.catsanddogstelegram.component;

import com.example.catsanddogstelegram.entity.CatAdopter;
import com.example.catsanddogstelegram.entity.DogAdopter;
import com.example.catsanddogstelegram.exception.CatNotFoundException;
import com.example.catsanddogstelegram.exception.DogNotFoundException;
import com.example.catsanddogstelegram.record.CatAdopterRecord;
import com.example.catsanddogstelegram.record.DogAdopterRecord;
import com.example.catsanddogstelegram.repository.CatRepository;
import com.example.catsanddogstelegram.repository.DogRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RecordMapper {
    private final DogRepository dogRepository;
    private final CatRepository catRepository;

    public DogAdopter toEntity(DogAdopterRecord record){
        DogAdopter entity = new DogAdopter();
        entity.setChatId(record.getChatId());
        entity.setName(record.getName());
        entity.setAge(record.getAge());
        entity.setRegisterTime(record.getRegisterTime());
        entity.setEmail(record.getEmail());
        entity.setProneNumber(record.getProneNumber());
        entity.setAddress(record.getAddress());
        entity.setDog(dogRepository.findById(record.getDogId()).orElseThrow(() -> new DogNotFoundException(record.getDogId())));
        return entity;
    }

    public DogAdopterRecord toRecord(DogAdopter entity){
        DogAdopterRecord record = new DogAdopterRecord();
        record.setChatId(entity.getChatId());
        record.setName(entity.getName());
        record.setAge(entity.getAge());
        record.setRegisterTime(entity.getRegisterTime());
        record.setEmail(entity.getEmail());
        record.setProneNumber(entity.getProneNumber());
        record.setAddress(entity.getAddress());
        record.setDogId(entity.getDog().getAnimalId());
        return record;
    }

    public CatAdopter toEntity(CatAdopterRecord record){
        CatAdopter entity = new CatAdopter();
        entity.setChatId(record.getChatId());
        entity.setName(record.getName());
        entity.setAge(record.getAge());
        entity.setRegisterTime(record.getRegisterTime());
        entity.setEmail(record.getEmail());
        entity.setProneNumber(record.getProneNumber());
        entity.setAddress(record.getAddress());
        entity.setCat(catRepository.findById(record.getCatId()).orElseThrow(() -> new CatNotFoundException(record.getCatId())));
        return entity;
    }

    public CatAdopterRecord toRecord(CatAdopter entity){
        CatAdopterRecord record = new CatAdopterRecord();
        record.setChatId(entity.getChatId());
        record.setName(entity.getName());
        record.setAge(entity.getAge());
        record.setRegisterTime(entity.getRegisterTime());
        record.setEmail(entity.getEmail());
        record.setProneNumber(entity.getProneNumber());
        record.setAddress(entity.getAddress());
        record.setCatId(entity.getCat().getAnimalId());
        return record;
    }
}

