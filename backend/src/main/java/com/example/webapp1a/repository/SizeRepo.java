package com.example.webapp1a.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webapp1a.model.Size;

public interface SizeRepo extends JpaRepository<Size, Integer>{
    
}
