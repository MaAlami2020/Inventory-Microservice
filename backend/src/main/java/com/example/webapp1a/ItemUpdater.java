package com.example.webapp1a;

import com.example.webapp1a.model.Item;

public interface ItemUpdater {
    void update(Item oldItem, Item newItem);
}
