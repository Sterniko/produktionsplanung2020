package de.adventureworks.produktionsplanung.model.services.productionTrial;

import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ProductionSimulationUtil {

    private ProductionSimulationUtil() {

    }

    //returns Orders sorted by supplier
    static Map<Supplier, Map<Component, Integer>> transformDailyProductionToComponents(Map<Bike, Integer> order) {

        Map<Supplier, Map<Component, Integer>> supplierSortedOrder = new HashMap<>();

        Map<Component, Integer> componentsToBeOrdered = new HashMap<>();

        for (Bike bike : order.keySet()) {
            int orderAmount = order.get(bike);
            for (Component c : bike.getComponents()) {
                if (componentsToBeOrdered.containsKey(c)) {
                    componentsToBeOrdered.put(c, componentsToBeOrdered.get(c) + orderAmount);
                } else {
                    componentsToBeOrdered.put(c, orderAmount);
                }
            }
        }

        for (Component c : componentsToBeOrdered.keySet()) {
            Supplier s = c.getSupplier();
            int orderAmount = componentsToBeOrdered.get(c);
            if (!supplierSortedOrder.containsKey(s)) {
                supplierSortedOrder.put(s, new HashMap<>());
            }
            if (supplierSortedOrder.get(s).containsKey(c)) {
                supplierSortedOrder.get(s).put(c, supplierSortedOrder.get(s).get(c) + orderAmount);

            } else {
                supplierSortedOrder.get(s).put(c, orderAmount);
            }

        }

        return supplierSortedOrder;

    }

    static Map<Component, Integer> addDeliveriesToWarehouseStock(Map<Component, Integer> warehouseStock, List<LogisticsObject> deliveries) {
        for (LogisticsObject lo : deliveries) {
            for (Component component : lo.getComponents().keySet()) {
                warehouseStock.put(component, lo.getComponents().get(component));
            }
        }
        return warehouseStock;
    }

    //gibt maximale mögliche Produktion zurück. Wenn dailyShouldProduction möglich ist, ist sie der Rückgabewert. Effektiv wird die actualProduction berechnet.
    static Map<Bike, Integer> tryToAchieveDailyProduction(Map<Bike, Integer> dailyShouldProduction, Map<Component, Integer> wareHouseStock, int maxProdCapacity) {
        int cap = maxProdCapacity;
        Map<Bike ,Integer> actuallyPossibleProduction = new HashMap<>();
        for (Bike bike : dailyShouldProduction.keySet()) {
            int achievdProductions = 1;
            List<Component> neededComponents = bike.getComponents();
            int neededAmount = dailyShouldProduction.get(bike);
            for (int i = 0; i < neededAmount && cap > 0; i++) {
                int forkAmount= wareHouseStock.get(neededComponents.get(0));
                int frameAmount=wareHouseStock.get(neededComponents.get(1));
                int saddleAmount= wareHouseStock.get(neededComponents.get(2));
                if (forkAmount>0&&frameAmount>0&&saddleAmount>0) {
                    wareHouseStock.put(neededComponents.get(0),forkAmount-1);
                    wareHouseStock.put(neededComponents.get(1),frameAmount-1);
                    wareHouseStock.put(neededComponents.get(2),saddleAmount-1);
                    actuallyPossibleProduction.put(bike,achievdProductions++);
                }
            }
        }
        return actuallyPossibleProduction;
    }

    static int countBikes(Map<Bike, Integer> bikes) {
        int sum= 0;
        for(Bike bike : bikes.keySet()){
           sum+= bikes.get(bike);
        }
        return sum;
    }


    static Map<Component, Integer> substractProductionFromWarehouse(Map<Bike, Integer> production, Map<Component, Integer> warehouse) {
        for(Bike bike: production.keySet()){
            List<Component> bikeComponent = bike.getComponents();
            for(Component component: bikeComponent){
                int compStock= warehouse.get(component);
                int subtractAmount= production.get(bike);
                if(subtractAmount>compStock){
                    throw new IllegalArgumentException("Du wolltest mehr löschen als überhaupt da war,... du Penner!");
                }
                warehouse.put(component,compStock-subtractAmount);
            }
        }
        return warehouse;
    }


    static <T> Map<T, Integer> addMaps(Map<T, Integer> map1, Map<T, Integer> map2) {
        Map<T,Integer> result= new HashMap<>();
        for(T e: map1.keySet()){
            if(map2.containsKey(e)){
                result.put(e,map1.get(e)+map2.get(e));
            }else{
                result.put(e,map1.get(e));
            }
        }
        for(T e: map2.keySet()){
            if(!map1.containsKey(e)){
                result.put(e,map1.get(e));
            }
        }

        return result;
    }

    static <T> Map<T, Integer> substractMaps(Map<T, Integer> minuend, Map<T, Integer> subtrahend){
        for(T e: subtrahend.keySet()){
            if(!minuend.containsKey(e)){
                throw new IllegalArgumentException("Die du willst ein Element aus der Map löschen was gar nicht vorhanden ist");
            }
            else if(subtrahend.get(e)>minuend.get(e)){
                throw new IllegalArgumentException("Du möchtest mehr aus der Map löschen als vorhanden ist, das Problem ist bei"+e);
            }
            else{
                int actualValue= minuend.get(e);
                minuend.put(e,actualValue-subtrahend.get(e));
            }
        }
        return minuend;
    }


}
