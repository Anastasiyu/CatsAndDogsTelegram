package com.example.catsanddogstelegram.repository;

import com.example.catsanddogstelegram.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends JpaRepository<Cat, Integer> {
}
