package com.example.webapp1a.Service;

import java.sql.Blob;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Item> findAll(Pageable page){
        return itemRepo.findAll(page);
    }
    
    public Optional<Item> findById(Integer id){
        return itemRepo.findById(id);
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
        }

        newItem.setId(id);
        itemRepo.save(newItem);
    }
}
