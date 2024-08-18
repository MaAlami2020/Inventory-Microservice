package com.example.webapp1a.controller;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemsController {

    @GetMapping("/items")
    public String getItems(Model model, Pageable page){

        //Page<Item> items = itemService.findAll(PageRequest.of(0,10));
        //model.addAttribute("items", items);
        return "index";
    }
    
}
