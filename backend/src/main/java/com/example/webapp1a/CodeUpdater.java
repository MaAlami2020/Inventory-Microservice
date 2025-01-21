package com.example.webapp1a;

import com.example.webapp1a.model.Item;

public class CodeUpdater implements ItemUpdater{

    @Override
    public void update(Item oldItem, Item newItem) {
        if(newItem.getCode() != null && !newItem.getCode().isEmpty()){
            oldItem.setCode(newItem.getCode());
        }
    }
    
}
