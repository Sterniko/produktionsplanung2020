package de.adventureworks.produktionsplanung.model.entities.events;

import de.adventureworks.produktionsplanung.model.entities.bike.Component;

import java.time.LocalDate;
import java.util.Map;

public class DeliveryChangeEvent implements IEvent {

    private String id;

    private Map<Component, Integer> newComponents;

    public DeliveryChangeEvent(String id, Map<Component, Integer> newComponents) {
        this.id = id;
        this.newComponents = newComponents;
    }

    public String getId() {
        return id;
    }

    public Map<Component, Integer> getNewComponents() {
        return newComponents;
    }
}
