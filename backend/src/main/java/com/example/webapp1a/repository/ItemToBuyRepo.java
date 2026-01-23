package com.example.webapp1a.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webapp1a.model.ItemToBuy;


public interface ItemToBuyRepo extends JpaRepository<ItemToBuy, Integer>{

    
}
