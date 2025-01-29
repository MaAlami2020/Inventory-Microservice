package com.example.webapp1a.sizeFactoryMethod;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.webapp1a.model.Size;
import com.example.webapp1a.service.SizeService;

public class SizeFactoryManager {

    @Autowired
    private static SizeService sizeService;

    //private static List<Size> sizes = new ArrayList<>();

    /*public SizeFactoryManager() {
        // Valores iniciales
        sizes.add(new Size("S"));
        sizes.add(new Size("M"));
        sizes.add(new Size("L"));
        sizes.add(new Size("XL"));
        sizes.add(new Size("38"));
        sizes.add(new Size("39"));
        sizes.add(new Size("41"));
        sizes.add(new Size("42"));
        sizes.add(new Size("43"));
        sizes.add(new Size("44"));
        sizes.add(new Size("46"));
    }

    public List<Size> getSizes() {
        return sizes;
    }*/

    public static void addSize(String newSize) {
        sizeService.add(new Size(newSize));
        //sizes.add(new Size(newSize));
    }
}
