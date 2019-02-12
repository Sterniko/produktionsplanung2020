package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.entities.bike.Component;

import java.util.HashMap;
import java.util.Map;

public class WarehouseService {

    private WarehouseService() {

    }

    public static Map<Component, Integer> determineOrderAmount(
            Map<Component, Integer> beforeWarehouseStock, Map<Component, Integer> afterWarehouseStock) {
        Map<Component, Integer> orderMap = new HashMap<>();

        for (Component c: afterWarehouseStock.keySet()) {
            Integer amountToOrder = beforeWarehouseStock.get(c) - afterWarehouseStock.get(c);
            if (amountToOrder > 0) {
                orderMap.put(c, amountToOrder);
            }
        }

        return orderMap;
    }

}
