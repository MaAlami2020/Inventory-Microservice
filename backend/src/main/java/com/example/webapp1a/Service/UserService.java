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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User add(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPasswordConfirmation(passwordEncoder.encode(user.getPasswordConfirmation()));
        return userRepo.save(user);
    }

    public Optional<User> findById(Integer id){
        return userRepo.findById(id);
    }

    public Optional<User> findByUsername(String name){
        return userRepo.findByUsername(name);
    }
    
}
