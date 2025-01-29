package com.example.webapp1a.stockEditionFactoryMethod;

import com.example.webapp1a.model.Clothes;
import com.example.webapp1a.model.Item;
import com.example.webapp1a.model.Size;
import com.example.webapp1a.model.Stock;

public class ClothesStockFactory implements StockFactory{

    @Override
    /*
     * create a clothes object which derivates from abstract class of stock
     */
    public Stock<?> createStock(Item item, String code, Size size, Integer stock) {

        Clothes clothes = new Clothes();

        //apply to the particular stock created the entry data
        clothes.setItem(item);
        if(code != null && !code.isEmpty()){
            clothes.setCode(code);
        }
        if(size != null){
            clothes.setSize(size);
        }
        if(stock != null){
            clothes.setStock(stock);
        }
        return clothes;
    } 
}
