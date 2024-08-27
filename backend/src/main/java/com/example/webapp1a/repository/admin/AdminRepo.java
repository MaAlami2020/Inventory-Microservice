package com.example.webapp1a.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webapp1a.model.admin.Admin;

public interface AdminRepo extends JpaRepository<Admin, Integer>{
    
}
