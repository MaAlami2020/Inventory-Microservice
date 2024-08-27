package com.example.webapp1a.repository.items;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.webapp1a.model.items.Item;

public interface ItemRepo extends JpaRepository<Item, Integer> {

    @Query("select m from Item m where m.name like %:name% or m.description like %:name% or m.sex like %:name% or m.category like %:name% or m.size like %:name%")
    Page<Item> findByName(String name, Pageable page);
}

