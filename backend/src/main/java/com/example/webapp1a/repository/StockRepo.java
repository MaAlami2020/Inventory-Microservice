package com.example.webapp1a.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.webapp1a.model.Item;
import com.example.webapp1a.model.Stock;

public interface StockRepo extends JpaRepository<Stock<?>, Integer> {

    @Query("select s from Stock s where s.item=?1 ")
    Page<Stock<?>> findByItem(Item item, Pageable page);

    @Query("select m from Stock m where m.item is null")
    Page<Stock<?>> findAllStocks(Pageable page);
    
}
