package com.example.catsanddogstelegram.repository;

import com.example.catsanddogstelegram.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
