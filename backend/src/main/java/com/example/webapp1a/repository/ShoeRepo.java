package com.example.webapp1a.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.webapp1a.model.Shoe;

public interface ShoeRepo extends JpaRepository<Shoe, Integer>{

    @Query("select s from Shoe s where s.item is null")
    Page<Shoe> findShoe(Pageable page);

    Optional<Shoe> findByCode(String code);
    
}
