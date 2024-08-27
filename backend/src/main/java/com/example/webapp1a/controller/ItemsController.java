package com.example.webapp1a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.webapp1a.model.items.Item;
import com.example.webapp1a.service.ItemService;


@Controller
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public String getItems(Model model, Pageable page){
        return "index";
    }
    
}
