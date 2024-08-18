package com.example.webapp1a.Model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_item")
public class Item {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    private boolean image;

    @Lob
    @Column(name = "image")
    private Blob itemImage;

    public Item(){}

    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }

    public void setDescription(String description){
        this.description = description;
    }
    
    public String getDescription(){
        return description;
    }

    public void setPrice(Double price){
        this.price = price;
    }
    
    public Double getPrice(){
        return price;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public void setImage(boolean image){
        this.image = image;
    }

    public boolean getImage(){
        return image;
    }

    public void setImageFile(Blob image){
        this.itemImage = image;
    }

    public Blob getImageFile(){
        return itemImage;
    }
}

