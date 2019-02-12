package de.adventureworks.produktionsplanung.controller.util;

import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestMapper {

    private RequestMapper() {

    }

    public static Map<Component, Integer> mapComponentStringMap(Map<String, Integer> stringMap, List<Component> components) {

        Map<Component,Integer> returnMap = new HashMap<>();
        for(String cName: stringMap.keySet()) {
            for(Component c: components) {
                if(cName.equals(c.getName())) {
                    returnMap.put(c, stringMap.get(cName));
                    break;
                }
            }
        }

        return returnMap;
    }

    public static Bike mapBike(String bikeName, List<Bike> bikes) {

        for (Bike bike: bikes) {
            if (bike.getName().equals(bikeName)) {
                return bike;
            }
        }
        return null;

    }

    public static Ship mapShip(String shipName, List<Ship> ships) {

        for(Ship ship: ships) {
            if (ship.getName().equals(shipName)){
                return ship;
            }
        }
        return null;

    }

}
