package com.example.webapp1a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp1a.Model.Item;
import com.example.webapp1a.Service.ItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/databases")
public class ItemsRestController {

    //admin add items
    @Autowired
    private ItemService itemService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/item")
    public void addItem(@RequestBody Item item) {
        itemService.add(item);
    }
}
