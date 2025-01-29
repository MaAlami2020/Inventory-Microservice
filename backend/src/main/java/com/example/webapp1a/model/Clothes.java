package com.example.webapp1a.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Clothes extends Stock<Size>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private String size;

    public Clothes(){}

    @Override
    public void setSize(Size size){
        this.size=size.getLabel();
    }

    @Override
    public Size getSize(){
        return new Size(size);
    }
}
