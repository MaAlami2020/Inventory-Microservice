package com.example.webapp1a.model;

import javax.persistence.Table;

@Table(name= "tbl_stockDTO")
public class StockDTO {

    private Integer stock;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
}
