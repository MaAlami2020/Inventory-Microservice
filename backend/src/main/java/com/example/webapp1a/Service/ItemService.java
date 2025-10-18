package com.example.webapp1a.service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.webapp1a.itemEditionScalability.ItemUpdateManager;
import com.example.webapp1a.model.Item;
import com.example.webapp1a.repository.ItemRepo;

@Service
public class ItemService {

    @Autowired
    private ItemRepo itemRepo;

    public void add(Item item){
        item.setCode(UUID.randomUUID().toString().toUpperCase().substring(0, 7));
        itemRepo.save(item);
    }

    public void save(Item oldItem, Item newItem){
        ItemUpdateManager updateManager = new ItemUpdateManager();
        updateManager.applyUpdates(oldItem, newItem);
        
        itemRepo.save(oldItem);
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

    public void update(Item oldItem, Item newItem) throws IOException{
        if(newItem != null && newItem.getCode() != null){
            oldItem.setCode(newItem.getCode());
        }
        if(newItem != null && newItem.getName() != null){
            oldItem.setName(newItem.getName());
        }
        if(newItem != null && newItem.getDescription() != null){
            oldItem.setDescription(newItem.getDescription());
        }
        if(newItem != null && newItem.getPrice() != null){
            oldItem.setPrice(newItem.getPrice());
        }
        if(newItem != null && newItem.getGender() != null){
            oldItem.setGender(newItem.getGender());
        }
        if(newItem != null && newItem.getType() != null){
            oldItem.setType(newItem.getType());
        }
        itemRepo.save(oldItem);
    }
}
