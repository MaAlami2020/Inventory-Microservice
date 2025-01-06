package com.example.webapp1a.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.webapp1a.model.Clothes;

public interface ClothesRepo extends JpaRepository<Clothes, Integer> {

    @Query("select c from Clothes c where c.item is null")
    Page<Clothes> findClothes(Pageable page);

    Optional<Clothes> findByCode(String code);
}

