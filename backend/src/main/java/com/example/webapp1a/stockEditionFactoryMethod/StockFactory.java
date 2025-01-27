package com.example.webapp1a.stockEditionFactoryMethod;

import com.example.webapp1a.model.Item;
import com.example.webapp1a.model.Stock;
import com.example.webapp1a.sizeFactoryMethod.Size;

public interface StockFactory {
    Stock<?> createStock(Item item, String code, Size size, Integer stock);
}
