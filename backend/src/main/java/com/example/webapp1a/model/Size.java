package com.example.webapp1a.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.example.webapp1a.sizeFactoryMethod.SizeFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Size implements SizeFactory{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String label;

    @OneToOne(mappedBy="size")
    @JsonIgnore
    private Stock<?> stock;

    public Size(){}

    public Size(String label) {
        this.label = label;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

    public void setStock(Stock<?> stock){
        this.stock = stock;
    }

    public Stock<?> getStock(){
        return stock;
    }
    
}
