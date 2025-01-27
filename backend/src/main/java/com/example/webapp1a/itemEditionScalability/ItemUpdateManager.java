package com.example.webapp1a.itemEditionScalability;
import java.util.ArrayList;
import java.util.List;

import com.example.webapp1a.model.Item;

public class ItemUpdateManager {
    private List<ItemUpdater> updaters = new ArrayList<>();

    //constructor
    public ItemUpdateManager() {
        updaters.add(new CodeUpdater());
        updaters.add(new NameUpdater());
        updaters.add(new DescriptionUpdater());
        updaters.add(new PriceUpdater());
        updaters.add(new PriceUpdater());
        updaters.add(new GenderUpdater());
        updaters.add(new TypeUpdater());
    }

    public void applyUpdates(Item oldItem, Item newItem){
        for(ItemUpdater updater : updaters){
            updater.update(oldItem, newItem);
        }
    }
    
}
