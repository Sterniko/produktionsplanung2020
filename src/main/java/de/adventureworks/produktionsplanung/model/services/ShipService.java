package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.entities.logistics.Delivery;


import java.time.LocalDate;
import java.util.List;


public class ShipService {

    //@Autowired
    //private DataBean databean;
    //TODO sollte eigentlich static sein,..... arbeite erstmal jetzt mit Injection---Sercan


    public void deleteShip(Ship ship, LocalDate deleteDay,DataBean databean ){
        databean.getShips().remove(ship);
        //TODO placeOrder neu bestellen---Sercan
    }

    public void delayShip(Ship ship, LocalDate newArrival, DataBean databean){
        ship.setArrival(newArrival);
        for (Delivery e : ship.getDeliveries()) {
            LocalDate oldDate = e.getArrival();
            BusinessDay oldBDay = databean.getBusinessDay(oldDate);
            oldBDay.getReceivedDeliveries().remove(e);
            databean.getBusinessDay(newArrival).getReceivedDeliveries().add(e);
            e.setArrival(newArrival);
        }
    }

    public Ship getShipByName(String name,DataBean databean){
        List<Ship> ships = databean.getShips();
        for(Ship e: ships){
            if(e.getName().equals(name)){
                return e;
            }
        }

        return null;

    }



}
