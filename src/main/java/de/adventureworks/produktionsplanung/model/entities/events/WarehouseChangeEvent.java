package de.adventureworks.produktionsplanung.model.entities.events;

import de.adventureworks.produktionsplanung.model.entities.bike.Component;

import java.util.Map;

public class WarehouseChangeEvent implements IEvent {

    private Map<Component, Integer> newWarehouseStock;

    public WarehouseChangeEvent(Map<Component, Integer> newWarehouseStock) {
        this.newWarehouseStock = newWarehouseStock;
    }

    public Map<Component, Integer> getNewWarehouseStock() {
        return newWarehouseStock;
    }
}
