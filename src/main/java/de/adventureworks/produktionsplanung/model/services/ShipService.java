package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.entities.logistics.Delivery;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class ShipService {

    @Autowired
    static private DataBean databean;


    public static void deleteShip(Ship ship, LocalDate deleteDay ){
        databean.getShips().remove(ship);
        //TODO placeOrder neu bestellen
    }

    public static void delayShip(Ship ship, LocalDate newArrival){
        ship.setArrival(newArrival);
        for (Delivery e : ship.getDeliveries()) {
            LocalDate oldDate = e.getArrival();
            BusinessDay oldBDay = databean.getBusinessDay(oldDate);
            oldBDay.getReceivedDeliveries().remove(e);
            databean.getBusinessDay(newArrival).getReceivedDeliveries().add(e);
            e.setArrival(newArrival);
        }
    }

    public static Ship getShipByName(String name){
        List<Ship> ships = databean.getShips();
        for(Ship e: ships){
            if(e.getName().equals(name)){return e;}
        }
        return null;
    }



}
