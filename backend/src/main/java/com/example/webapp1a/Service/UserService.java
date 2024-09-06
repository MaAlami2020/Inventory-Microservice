package com.example.webapp1a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.example.webapp1a.model.items.User;
import com.example.webapp1a.repository.items.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User add(User user){
        user.setEncodedPassword(passwordEncoder.encode(user.getEncodedPassword()));

        return userRepo.save(user);
    }

    public Optional<User> findById(Integer id){
        return userRepo.findById(id);
    }

    public Optional<User> findByEmail(String email){
        return userRepo.findByEmail(email);
    }

}
