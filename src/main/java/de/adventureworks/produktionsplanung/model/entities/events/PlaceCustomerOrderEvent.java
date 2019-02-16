package de.adventureworks.produktionsplanung.model.entities.events;

import de.adventureworks.produktionsplanung.model.entities.bike.Bike;

import java.time.LocalDate;
import java.util.Map;

public class PlaceCustomerOrderEvent implements IEvent {

    private LocalDate productionFinishDate;

    private Map<Bike, Integer> order;

    private boolean isPrio;

    public PlaceCustomerOrderEvent(LocalDate productionFinishDate, Map<Bike, Integer> order, boolean isPrio) {
        this.productionFinishDate = productionFinishDate;
        this.order = order;
        this.isPrio = isPrio;
    }

    public LocalDate getProductionFinishDate() {
        return productionFinishDate;
    }

    public Map<Bike, Integer> getOrder() {
        return order;
    }

    public boolean isPrio() {
        return isPrio;
    }
}
