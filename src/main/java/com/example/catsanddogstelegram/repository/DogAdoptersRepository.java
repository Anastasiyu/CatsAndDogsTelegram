package com.example.catsanddogstelegram.repository;

import com.example.catsanddogstelegram.entity.DogAdopter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogAdoptersRepository extends JpaRepository<DogAdopter, Long> {
}
