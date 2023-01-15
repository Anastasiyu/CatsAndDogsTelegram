package com.example.catsanddogstelegram.repository;

import com.example.catsanddogstelegram.entity.CatAdopter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatAdoptersRepository extends JpaRepository<CatAdopter, Long> {
}
