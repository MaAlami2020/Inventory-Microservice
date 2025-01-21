package com.example.webapp1a;

import com.example.webapp1a.model.Item;

public class NameUpdater implements ItemUpdater{

    @Override
    public void update(Item oldItem, Item newItem) {
        if(newItem.getName() != null && !newItem.getName().isEmpty()){
            oldItem.setName(newItem.getName());
        }
    }
    
}
