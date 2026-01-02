package com.example.webapp1a.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.webapp1a.model.User;
import com.example.webapp1a.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;


    public Optional<User> findById(Integer id){
        return userRepo.findById(id);
    }

    public Optional<User> findByUsername(String name){
        return userRepo.findByUsername(name);
    }
    
}
