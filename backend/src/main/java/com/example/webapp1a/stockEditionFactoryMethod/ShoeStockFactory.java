package com.example.webapp1a.stockEditionFactoryMethod;

import com.example.webapp1a.model.Item;
import com.example.webapp1a.model.Shoe;
import com.example.webapp1a.model.Stock;
import com.example.webapp1a.sizeFactoryMethod.Size;

public class ShoeStockFactory implements StockFactory {

    @Override
    public Stock<?> createStock(Item item, String code, Size size, Integer stock) {
        
        Shoe shoe = new Shoe();

        //apply to the particular stock created the entry data
        shoe.setItem(item);
        if(code != null && !code.isEmpty()){
            shoe.setCode(code);
        }
        if(size != null){
            shoe.setSize(size);
        }
        if(stock != null){
            shoe.setStock(stock);
        }
        return shoe;
    }
}
