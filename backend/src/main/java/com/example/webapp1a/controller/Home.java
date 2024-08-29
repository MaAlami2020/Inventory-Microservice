package com.example.webapp1a.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class Home {

    @ModelAttribute
    public void addAttribute(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();

        if(principal != null){
            model.addAttribute("logged",true);
        } else {
            model.addAttribute("logged",false);
        }
    }
    
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

    @GetMapping("/user/{username}/image")
    public String getUserAvatar(Model model, @PathVariable String username, MultipartFile imageField){
        
        
        model.addAttribute("picture");

        return "login";
    }
    
}
