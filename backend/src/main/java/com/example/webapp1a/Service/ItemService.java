package com.example.webapp1a.service;

import java.sql.Blob;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.webapp1a.model.items.Item;
import com.example.webapp1a.repository.items.ItemRepo;

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

    public void deleteById(Integer id){
        itemRepo.deleteById(id);
    }

    public Page<Item> getItemsByName(String name, Pageable page){
        return itemRepo.findByName(name, page);
    }

    public void update(Integer id, Item newItem){
        Optional<Item> item = itemRepo.findById(id);

        Blob image = item.get().getImageFile();
        newItem.setImageFile(image);
        
        if(newItem.getName() == null){
            newItem.setName(item.get().getName());
        }if(newItem.getCategory() == null){
            newItem.setCategory(item.get().getCategory());
        }if(newItem.getDescription() == null){
            newItem.setDescription(item.get().getDescription());
        }if(newItem.getPrice() == null){
            newItem.setPrice(item.get().getPrice());
        }if(newItem.getSex() == null){
            newItem.setSex(item.get().getSex());
        }if(newItem.getSize() == null){
            newItem.setSize(item.get().getSize());
        }if(newItem.getQuantity() == null){
            newItem.setQuantity(item.get().getQuantity());
        }

        newItem.setId(id);
        itemRepo.save(newItem);
    }
}
