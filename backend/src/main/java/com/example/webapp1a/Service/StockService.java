package com.example.webapp1a.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.webapp1a.model.Clothes;
import com.example.webapp1a.model.Shoe;
import com.example.webapp1a.repository.ClothesRepo;
import com.example.webapp1a.repository.ShoeRepo;

@Service
public class StockService {

    @Autowired
    private ClothesRepo clothesRepo;

    @Autowired
    private ShoeRepo shoeRepo;

    public Page<Clothes> findAllClothes(Pageable page){
        return clothesRepo.findClothes(page);
    }

    public Optional<Clothes> findByCodeC(String code){
        return clothesRepo.findByCode(code);
    }    
    
    public Clothes addClothes(Clothes clothes){
        return clothesRepo.save(clothes);
    }

    public Page<Shoe> findAllShoe(Pageable page){
        return shoeRepo.findShoe(page);
    }

    public Optional<Shoe> findByCodeS(String code){
        return shoeRepo.findByCode(code);
    }

    public Shoe addShoe(Shoe shoe){
        return shoeRepo.save(shoe);
    }
}
