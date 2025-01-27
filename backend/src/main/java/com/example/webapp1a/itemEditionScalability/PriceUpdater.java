package com.example.webapp1a.itemEditionScalability;

import com.example.webapp1a.model.Item;

public class PriceUpdater implements ItemUpdater{

    @Override
    public void update(Item oldItem, Item newItem) {
        if(newItem.getPrice() != null && newItem.getPrice().isNaN()){
            oldItem.setPrice(newItem.getPrice());
        }
    }
    
}
