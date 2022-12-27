package com.example.catsanddogstelegram.repository;

import com.example.catsanddogstelegram.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
