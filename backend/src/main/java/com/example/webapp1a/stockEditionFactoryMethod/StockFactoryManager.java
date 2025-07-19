package com.example.webapp1a.stockEditionFactoryMethod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StockFactoryManager {
    //variable which manages diferent types of products according to the facotry which made it
    private static final Map<String, String> factories = new HashMap<>();

    private static Set<String> genders = new HashSet<>();
        
    public StockFactoryManager() {
        factories.put("jeans", "clothes");
        factories.put("camisa", "clothes");
        factories.put("camiseta", "clothes");
        factories.put("zapato", "shoes");

        genders.add("man");
        genders.add("woman");
        genders.add("unisex");
    }

    public static Map<String, String> getFactories(){
        return factories;
    }

    public static Set<String> getGenders(){
        return genders;
    }
}
