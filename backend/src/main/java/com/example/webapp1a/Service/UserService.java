package com.example.webapp1a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.example.webapp1a.model.items.User;
import com.example.webapp1a.repository.items.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    
    public User add(User user){
        if(!user.getEmail().equals("") && !user.getPassword().equals("") && user.getConfirmationPassword().equals(user.getPassword())){
            return userRepo.save(user);
        } else {
            return null;
        }
    }

    public Optional<User> findById(Integer id){
        return userRepo.findById(id);
    }
}
