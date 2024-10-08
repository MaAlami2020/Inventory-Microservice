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
        Optional<Item> item = itemRepo.findById(id);
        if(newItem.getImageFile() == null){
            newItem.setImageFile(item.get().getImageFile());
        }
        if(newItem.getName().equals("")){
            newItem.setName(item.get().getName());
        }
        if(newItem.getPrice() == null){
            newItem.setPrice(item.get().getPrice());
        }
        if(newItem.getGender() == null){
            newItem.setGender(item.get().getGender());
        }
        if(item.get().getSizes() != null){
            int count = 0;
            for(String size: item.get().getSizes()){
                if(newItem.getSizes()[count] == null){
                    newItem.getSizes()[count] = size;
                }
                count++;
            }
        }
        if(item.get().getSizes() != null){
            int count1 = 0;
            for(Integer stock: item.get().getStocks()){
                if(newItem.getStocks()[count1] == null){
                    newItem.getStocks()[count1] = stock;
                }
                count1++;
            }
        }
        if(newItem.getType() == null){
            newItem.setType(item.get().getType());
        }
        if(newItem.getStocks() == null){
            newItem.setStocks(item.get().getStocks());
        }
        if(newItem.getDescription().equals("")){
            newItem.setDescription(item.get().getDescription());
        }
        newItem.setId(id);
        itemRepo.save(newItem);
    }
}
