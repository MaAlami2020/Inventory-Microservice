package com.example.webapp1a.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.webapp1a.Model.Item;

public interface ItemRepo extends JpaRepository<Item, Integer> {

    @Query("select m from Item m where m.sex like ?1")
    Page<Item> findByName(String name, Pageable page);
}
