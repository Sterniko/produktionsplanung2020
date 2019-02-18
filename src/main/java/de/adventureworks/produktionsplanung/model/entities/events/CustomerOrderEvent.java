package de.adventureworks.produktionsplanung.model.entities.events;

import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;

import java.time.LocalDate;
import java.util.Map;

public class CustomerOrderEvent implements IEvent {

    private boolean isPrio;

    private Map<Bike, Integer> orderAmount;

    private LocalDate dueProductionDate;

    public CustomerOrderEvent(boolean isPrio, Map<Bike, Integer> orderAmount, LocalDate dueProductionDate) {
        this.isPrio = isPrio;
        this.orderAmount = orderAmount;
        this.dueProductionDate = dueProductionDate;
    }

    public CustomerOrderEvent() {}

    public boolean isPrio() {
        return isPrio;
    }

    public Map<Bike, Integer> getOrderAmount() {
        return orderAmount;
    }

    public LocalDate getDueProductionDate() {
        return dueProductionDate;
    }
}