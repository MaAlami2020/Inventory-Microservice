package com.example.webapp1a.model;

import javax.persistence.Entity;

import com.example.webapp1a.sizeFactoryMethod.Size;

@Entity
public class Shoe extends Stock<Size> {
    //crear clases en vez de enumerados para hacer las clases mas abstractas, un objeto para cada talla


    private String size;
    
    public Shoe(){}

    @Override
    public void setSize(Size size) {
        this.size=size.getLabel();
    }

    @Override
    public Size getSize() {
        return new Size(size);
    }
}
