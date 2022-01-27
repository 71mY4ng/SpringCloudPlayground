package com.timyang.playground.transaction.entitys;

import java.util.Map;

public class Inventory {

    Map<Item, Integer> carried;

    public Map<Item, Integer> getCarried() {
        return carried;
    }
}
