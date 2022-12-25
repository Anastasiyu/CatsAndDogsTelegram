package com.example.catsanddogstelegram.repository;

import com.example.catsanddogstelegram.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    Collection<Dog> findAllByAnimalId(Long animalId);
}
