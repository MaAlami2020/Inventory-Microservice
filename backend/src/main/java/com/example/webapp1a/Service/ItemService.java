package com.example.webapp1a.service;

import java.sql.Blob;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.webapp1a.model.Item;
import com.example.webapp1a.repository.ItemRepo;

@Service
public class ItemService {

    @Autowired
    private ItemRepo itemRepo;

    public void add(Item item){
        itemRepo.save(item);
    }

    public Page<Item> findAll(Pageable page){
        return itemRepo.findAll(page);
    }
    
    public Optional<Item> findById(Integer id){
        return itemRepo.findById(id);
    }

    public Page<Item> findByName(String name, Pageable page){
        return itemRepo.findByName(name, page);
    }

    public void deleteById(Integer id){
        itemRepo.deleteById(id);
    }  

    public void update(Integer id, Item newItem){
        newItem.setId(id);
        itemRepo.save(newItem);
    }
}
