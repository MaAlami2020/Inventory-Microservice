package com.example.webapp1a.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webapp1a.model.Item;

public interface ItemRepo extends JpaRepository<Item, Integer> {

}
