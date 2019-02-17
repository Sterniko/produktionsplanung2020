package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.events.IEvent;
import de.adventureworks.produktionsplanung.model.entities.events.WarehouseChangeEvent;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseService {


    private WarehouseService() {

    }

    public static Map<Component, Integer> determineOrderAmount(
            Map<Component, Integer> beforeWarehouseStock, Map<Component, Integer> afterWarehouseStock) {
        Map<Component, Integer> orderMap = new HashMap<>();

        for (Component c: afterWarehouseStock.keySet()) {
            Integer amountToOrder = beforeWarehouseStock.get(c) - afterWarehouseStock.get(c);
            if (amountToOrder > 0) {
                orderMap.put(c, amountToOrder);
            }
        }

        return orderMap;
    }

    public void startEvent(BusinessDay bd, Map<Component, Integer> warehouseStock){
        WarehouseChangeEvent wce = new WarehouseChangeEvent(warehouseStock);
        List<IEvent> eventList = bd.getEventList();
        eventList.add(wce);
    }


}
