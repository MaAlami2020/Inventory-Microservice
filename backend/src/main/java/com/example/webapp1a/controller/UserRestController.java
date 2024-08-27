package com.example.webapp1a.controller;

import java.util.Optional;
import java.net.URI;
import java.sql.SQLException;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.webapp1a.model.items.User;
import com.example.webapp1a.service.UserService;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/databases")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.add(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @PostMapping("/user/{id}/image")
    public ResponseEntity<User> addUserImage(@PathVariable Integer id, @RequestParam MultipartFile avatar) throws IOException {
        
        Optional<User> user = userService.findById(id);
        
        URI location = fromCurrentRequest().build().toUri();
        
        user.get().setAvatar(BlobProxy.generateProxy(avatar.getInputStream(), avatar.getSize()));
        
        userService.add(user.get());
        
        if(user.isPresent()) {
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    


    /*@GetMapping("/users/{username}/image")
    public String getUserImage(@PathVariable String username) {
        
        Optional<User> user = userService.findByUsername(username);
    }*/
}
