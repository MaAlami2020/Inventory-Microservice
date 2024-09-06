package com.example.webapp1a.controller;

import java.security.Principal;
import java.util.Optional;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.webapp1a.model.items.User;
import com.example.webapp1a.service.UserService;
import java.sql.Blob;


@Controller
public class Home {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addAttribute(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();

        if(principal != null){
            String email = principal.getName();
            Optional<User> user = userService.findByEmail(email);
            model.addAttribute("iD",user.get().getId());
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
        model.addAttribute("state","");
        return "login";
    }

    @PostMapping("/new")
    public String newUser(Model model, User user, MultipartFile imageField) throws IOException{

        if(imageField.isEmpty()){
            Optional<User> anonymous = userService.findById(39);
            if(!anonymous.isPresent()) {
                user.setImageFile(anonymous.get().getImageFile());
            }
        }else{
            user.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
        }

        user.setRol("USER");

        User userSaved = userService.add(user);

        if(userSaved != null){
            model.addAttribute("state_reg", "user registered");
        }else{
            model.addAttribute("state_reg", "some mnadatory fields are empty or incorrect");
        }
        model.addAttribute("state_log", "");

        return "login";
    }

    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("state","");
        return "login";
    }

    @GetMapping("/user/{username}/image")
    public String getUserAvatar(Model model, @PathVariable String username, MultipartFile imageField){
        
        
        model.addAttribute("picture");

        return "login";
    }
    
}
