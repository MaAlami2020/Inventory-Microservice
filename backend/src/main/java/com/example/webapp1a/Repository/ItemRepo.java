package com.example.webapp1a.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webapp1a.Model.Item;

public interface ItemRepo extends JpaRepository<Item, Integer> {
    
}
