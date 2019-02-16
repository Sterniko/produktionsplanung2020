package de.adventureworks.produktionsplanung.model.entities.events;

import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;

import java.util.Map;

public class ProductionIncreaseEvent implements IEvent {

    private BusinessWeek businessWeek;

    private Map<Bike, Integer> increaseAmount;

    public ProductionIncreaseEvent(BusinessWeek businessWeek, Map<Bike, Integer> increaseAmount) {
        this.businessWeek = businessWeek;
        this.increaseAmount = increaseAmount;
    }

    public BusinessWeek getBusinessWeek() {
        return businessWeek;
    }

    public Map<Bike, Integer> getIncreaseAmount() {
        return increaseAmount;
    }
}
