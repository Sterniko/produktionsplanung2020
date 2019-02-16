package de.adventureworks.produktionsplanung.model.entities.events;

import de.adventureworks.produktionsplanung.model.entities.external.Ship;

public class ShipDeleteEvent implements IEvent {

    private Ship ship;

    public ShipDeleteEvent(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

}
