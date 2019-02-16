package de.adventureworks.produktionsplanung.model.entities.events;

import de.adventureworks.produktionsplanung.model.entities.bike.Component;

import java.time.LocalDate;
import java.util.Map;

public class DeliveryChangeEvent implements IEvent {

    private LocalDate receiveDate;

    private Map<Component, Integer> lostComponents;

    public DeliveryChangeEvent(LocalDate receiveDate, Map<Component, Integer> lostComponents) {
        this.receiveDate = receiveDate;
        this.lostComponents = lostComponents;
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public Map<Component, Integer> getLostComponents() {
        return lostComponents;
    }
}
