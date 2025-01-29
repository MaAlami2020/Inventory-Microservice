package com.example.webapp1a.stockEditionFactoryMethod;

import com.example.webapp1a.model.Item;
import com.example.webapp1a.model.Size;
import com.example.webapp1a.model.Stock;

public interface StockFactory {
    Stock<?> createStock(Item item, String code, Size size, Integer stock);
}
