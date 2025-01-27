package com.example.webapp1a.stockEditionFactoryMethod;

import java.util.HashMap;
import java.util.Map;

import com.example.webapp1a.model.Item;
import com.example.webapp1a.model.Stock;
import com.example.webapp1a.sizeFactoryMethod.Size;

public class StockFactoryManager {
    private static Map<String, StockFactory> factories = new HashMap<>();
        
    public StockFactoryManager() {
        factories.put("jeans", new ClothesStockFactory());
        factories.put("camisa",new ClothesStockFactory());
        factories.put("camiseta",new ClothesStockFactory());
        factories.put("zapato",new ShoeStockFactory());
    }

    public Map<String, StockFactory> getFactories(){
        return factories;
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
