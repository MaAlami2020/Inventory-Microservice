package com.example.webapp1a.controller;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.webapp1a.model.items.User;
import com.example.webapp1a.service.UserService;

import java.io.IOException;
import java.util.Optional;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addAttribute(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();

        if(principal != null){
            model.addAttribute("logged",true);
        } else {
            model.addAttribute("logged",false);
        }
    }

    @RequestMapping("/login")
    public String accessUser(Model model, User user) {
        model.addAttribute("logged",true);
        return "index";
    }
    
    
    @PostMapping("/signup")
    public String newUser(Model model, User user, MultipartFile imageField, HttpServletRequest request) throws IOException{

        Optional<User> oldUser = userService.findById(39);

        if(!imageField.isEmpty()){
            user.setAvatar(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
        } else {
            user.setAvatar(oldUser.get().getAvatar());
        }

        User userSaved = userService.add(user);


        if(userSaved != null){
            model.addAttribute("state", "user registered");
        } else {
            model.addAttribute("state", "some fields are empty or incorrect");
        }
        return "login";
    }
}
