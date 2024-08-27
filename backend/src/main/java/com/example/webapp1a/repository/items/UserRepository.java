package com.example.webapp1a.repository.items;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webapp1a.model.items.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    
}
