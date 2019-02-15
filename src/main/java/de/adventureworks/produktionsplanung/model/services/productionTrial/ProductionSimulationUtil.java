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
        return null;
    }

    //gibt maximale mögliche Produktion zurück. Wenn dailyShouldProduction möglich ist, ist sie der Rückgabewert. Effektiv wird die actualProduction berechnet.
    static Map<Bike, Integer> tryToAchieveDailyProduction(Map<Bike, Integer> dailyShouldProduction, Map<Component, Integer> wareHouseStock, int maxProdCapacity) {
        return null;
    }

    static int countBikes(Map<Bike, Integer> bikes) {
        return 0;
    }


    static Map<Component, Integer> substractProductionFromWarehouse(Map<Bike, Integer> production, Map<Component, Integer> warehouse) {
        return null;

    }


    static <T> Map<T, Integer> addMaps(Map<T, Integer> map1, Map<T, Integer> map2) {
        return null;

    }

    static <T> Map<T, Integer> substractMaps(Map<T, Integer> minuend, Map<T, Integer> subtrahend) {
        return null;
    }


}
