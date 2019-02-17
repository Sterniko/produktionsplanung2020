package de.adventureworks.produktionsplanung.model.entities.events;

import de.adventureworks.produktionsplanung.model.entities.bike.Bike;

import java.time.LocalDate;
import java.util.Map;

public class PlaceCustomerOrderEvent implements IEvent {

    private LocalDate productionFinishDate;

    private Map<Bike, Integer> order;


    public PlaceCustomerOrderEvent(LocalDate productionFinishDate, Map<Bike, Integer> order) {
        this.productionFinishDate = productionFinishDate;
        this.order = order;

    }

    public LocalDate getProductionFinishDate() {
        return productionFinishDate;
    }

    public Map<Bike, Integer> getOrder() {
        return order;
    }


}
