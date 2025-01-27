package com.example.webapp1a.sizeFactoryMethod;

public class Size implements SizeFactory{

    private final String label;

    public Size(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
    
}
