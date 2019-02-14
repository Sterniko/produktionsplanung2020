package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.entities.logistics.Delivery;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class ShipService {

    @Autowired
    private DataBean databean;
    //TODO sollte eigentlich static sein---Sercan



    public ShipService(DataBean dataBean) {
        this.databean = dataBean;
    }

    public void deleteShip(Ship ship, LocalDate deleteDay, DataBean databean ){
        databean.getShips().remove(ship);
        //TODO placeOrder neu bestellen---Sercan
    }

    public void delayShip(Ship ship, LocalDate newArrival){
        ship.setArrival(newArrival);
        for (Delivery e : ship.getDeliveries()) {
            LocalDate oldDate = e.getArrival();
            BusinessDay oldBDay =  this.databean.getBusinessDay(oldDate);
            oldBDay.getReceivedDeliveries().remove(e);
            this.databean.getBusinessDay(newArrival).getReceivedDeliveries().add(e);
            e.setArrival(newArrival);
        }
    }

    public Ship getShipByName(String name){
        List<Ship> ships = this.databean.getShips();
        for(Ship e: ships){
            if(e.getName().equals(name)){
                return e;
            }
        }
        return null;
    }


    /**
     * Rechnet aus welches Schiff noch nicht aus China abgefahren ist und als frühstes los fährt von gewünschten Date aus
     * betrachtet
     *
     * @param todaysDate das Date von den aus bewertet wird welches Schiff als nächstes in Frage käme
     *
     * @return nächst mögliches aufbrechendes Schiff
     */
    public Ship getNextShip(LocalDate todaysDate){
        List<Ship> ships = databean.getShips();
        List<Ship> availableShips = new LinkedList<>();
        for(Ship available: ships){
            if(available.getDeparture().isAfter(todaysDate) || available.getDeparture().isEqual(todaysDate)){
                availableShips.add(available);
                //TODO frage ist ob auch schiffe die am heutigen Tag abfahren in frage kommen zweiter BoolTerm
            }
        }
        if(availableShips==null){
            System.out.println("Kein Schiff mehr Verfügbar");
            return null;
        }

        Ship nextAvailable = availableShips.get(0);
        for(Ship possibleEarlierShip:availableShips ){
            if(possibleEarlierShip.getDeparture().isBefore(nextAvailable.getDeparture())){
                nextAvailable=possibleEarlierShip;
            }
        }
        return nextAvailable;
    }

    public void fillShip(Ship ship, LogisticsObject logisticsObject){

    }

}
