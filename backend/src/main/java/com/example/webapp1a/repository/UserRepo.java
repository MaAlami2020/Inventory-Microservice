package com.example.webapp1a.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webapp1a.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{
    
    Optional<User> findByUsername(String name);
}
