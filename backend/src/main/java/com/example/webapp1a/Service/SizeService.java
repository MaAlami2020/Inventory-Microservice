package com.example.webapp1a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.webapp1a.model.Size;
import com.example.webapp1a.repository.SizeRepo;

@Service
public class SizeService {

    @Autowired
    private SizeRepo sizeRepo;
    
    public void add(Size size){
        sizeRepo.save(size);
    }

    public Page<Size> findAll(Pageable page){
        return sizeRepo.findAll(page);
    }
}
