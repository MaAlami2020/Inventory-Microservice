package com.example.webapp1a.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webapp1a.Model.Item;
import com.example.webapp1a.Repository.ItemRepo;

@Service
public class ItemService {

    @Autowired
    private ItemRepo itemRepo;

    public void add(Item item){
        itemRepo.save(item);
    }
    
    public Optional<Item> findById(Integer id){
        return itemRepo.findById(id);
    }
}
