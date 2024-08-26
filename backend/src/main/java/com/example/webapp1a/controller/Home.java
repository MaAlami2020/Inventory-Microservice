package com.example.webapp1a.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class Home {
    
    @GetMapping("/")
    public String home(Model model){
        return "index";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        return "login";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @GetMapping("/user/{id}/image")
    public String getUserAvatar(Model model, @PathVariable Long id, MultipartFile imageField){
        
        
        model.addAttribute("picture");

        return "login";
    }
    
}
