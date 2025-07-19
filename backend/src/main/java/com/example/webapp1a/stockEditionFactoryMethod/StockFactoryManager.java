package com.example.webapp1a.stockEditionFactoryMethod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.example.webapp1a.model.Item;
import com.example.webapp1a.model.Size;
import com.example.webapp1a.model.Stock;

public class StockFactoryManager {
    //variable which manages diferent types of products according to the facotry which made it
    private static Map<String, StockFactory> factories = new HashMap<>();

    private static Set<String> genders = new HashSet<>();
        
    public StockFactoryManager() {
        factories.put("jeans", new ClothesStockFactory());
        factories.put("camisa",new ClothesStockFactory());
        factories.put("camiseta",new ClothesStockFactory());
        factories.put("zapato",new ShoeStockFactory());

        genders.add("man");
        genders.add("woman");
        genders.add("unisex");
    }

    public static Map<String, StockFactory> getFactories(){
        return factories;
    }

    public static Set<String> getGenders(){
        return genders;
    }

    /* 
        method that creates a particualr factory based on the parameter of entry.
        The particular factory will create its particular object
    */
    public static Stock<?> createStock(String itemType, Item item, String code, Size size, Integer stock){
        StockFactory particularFactory = factories.get(itemType);
        if (particularFactory == null) {
            throw new IllegalArgumentException("Stock type not supported: " + itemType);
        }
        return particularFactory.createStock(item, code, size, stock);
    }
}
