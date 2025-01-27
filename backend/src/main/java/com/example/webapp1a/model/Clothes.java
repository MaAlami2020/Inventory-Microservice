package com.example.webapp1a.model;

import javax.persistence.Entity;

import com.example.webapp1a.sizeFactoryMethod.Size;

@Entity
public class Clothes extends Stock<Size>{

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
