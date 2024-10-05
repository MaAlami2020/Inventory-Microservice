package com.example.webapp1a.controller;

import java.io.IOException;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigData.Option;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.webapp1a.model.Item;
import com.example.webapp1a.service.ItemService;



@Controller
//@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemService itemService;
    
    @GetMapping("/")
    public String home(Model model){
        return "index";
    }    
  
    @GetMapping("/items/page")
    public String newItem(Model model){
        model.addAttribute("stock","");
        model.addAttribute("status","");
        return "new_item";
    }

    @PostMapping("/items/new")
    public String newItem(Model model, Item item, MultipartFile imageField) throws IOException{
        if(item.getStock() > 5){
            model.addAttribute("stock","In stock");
        } else {
            model.addAttribute("stock","Only 5 items left");
        }
        item.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
        itemService.add(item);
        model.addAttribute("status","item created"); 
        return "new_item";
    }
    
}
