package com.example.webapp1a;

import com.example.webapp1a.model.Item;

public class DescriptionUpdater implements ItemUpdater{

    @Override
    public void update(Item oldItem, Item newItem) {
        if(newItem.getDescription() != null && !newItem.getDescription().isEmpty()){
            oldItem.setDescription(newItem.getDescription());
        }
    }
    
}
