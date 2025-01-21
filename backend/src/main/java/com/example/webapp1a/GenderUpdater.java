package com.example.webapp1a;

import com.example.webapp1a.model.Item;

public class GenderUpdater implements ItemUpdater{

    @Override
    public void update(Item oldItem, Item newItem) {
        if(newItem.getGender() != null && !newItem.getGender().isEmpty()){
            oldItem.setGender(newItem.getGender());
        }
    }
    
}
