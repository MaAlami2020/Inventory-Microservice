package com.example.webapp1a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webapp1a.model.ItemToBuy;
import com.example.webapp1a.repository.ItemToBuyRepo;

@Service
public class ItemToBuyService {

    @Autowired
    private ItemToBuyRepo itemToBuyRepo;


    public List<ItemToBuy> findAll(){
        return itemToBuyRepo.findAll();
    }
}
