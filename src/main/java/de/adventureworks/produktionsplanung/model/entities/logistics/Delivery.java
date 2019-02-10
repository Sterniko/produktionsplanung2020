package de.adventureworks.produktionsplanung.model.entities.logistics;

import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;

import java.time.LocalDate;
import java.util.Map;

public class Delivery extends LogisticsObject {

    private LocalDate arrival;

    public Delivery(Supplier supplier, int sumAmount, Map<Component, Integer> components, LocalDate arrival) {
        super(supplier, sumAmount, components);
        this.arrival = arrival;
    }

    public LocalDate getArrival() {
        return arrival;
    }

    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }


}
