package com.example.webapp1a.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class Clothes extends Stock<Clothes.Size>{

    public enum Size{
        S, M, L, XL;

        public static String getSize(Size size){
            return size.name();
        }

        public static List<String> getSizes(){
            List<String> pairs = new ArrayList<>();
            for(Size clothes: Size.values()){
                pairs.add(getSize(clothes));
            }
            return pairs;
        }
    }

    private Size size;

    public Clothes(){}

    @Override
    public void setSize(Size size){
        this.size=size;
    }

    @Override
    public Size getSize(){
        return size;
    }
}
