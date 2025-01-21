package com.example.webapp1a;

import com.example.webapp1a.model.Item;

public class TypeUpdater implements ItemUpdater{

    @Override
    public void update(Item oldItem, Item newItem) {
        if(newItem.getType() != null && !newItem.getType().isEmpty()){
            oldItem.setType(newItem.getType());
        }
    }
    
}
