package com.example.webapp1a.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp1a.model.items.User;
import com.example.webapp1a.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/databases")
public class UserRestController {

    @Autowired
    private UserService userService;

    /*@GetMapping("/users/{username}/image")
    public String getUserImage(@PathVariable String username) {
        
        Optional<User> user = userService.findByUsername(username);
    }*/
    
    
}
