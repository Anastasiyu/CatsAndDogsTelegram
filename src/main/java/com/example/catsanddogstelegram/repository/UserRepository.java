package com.example.catsanddogstelegram.repository;

import com.example.catsanddogstelegram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
