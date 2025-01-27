package com.example.webapp1a.sizeFactoryMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SizeFactoryManager {

    private static final List<Size> sizes = new ArrayList<>();

    static {
        // Valores iniciales
        sizes.add(new Size("S"));
        sizes.add(new Size("M"));
        sizes.add(new Size("L"));
        sizes.add(new Size("XL"));
    }

    public static List<Size> getSizes() {
        return Collections.unmodifiableList(sizes);
    }

    public static void addSize(String newSize) {
        sizes.add(new Size(newSize));
    }
}
