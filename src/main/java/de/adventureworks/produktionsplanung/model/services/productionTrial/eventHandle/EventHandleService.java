package de.adventureworks.produktionsplanung.model.services.productionTrial.eventHandle;


import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.events.*;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import de.adventureworks.produktionsplanung.model.services.ArrivalCalculatorService;
import de.adventureworks.produktionsplanung.model.services.DeliveryService;
import de.adventureworks.produktionsplanung.model.services.OrderService;
import de.adventureworks.produktionsplanung.model.services.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class EventHandleService {


    @Autowired
    private DataBean dataBean;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DeliveryService deliveryService;

    public EventHandleService() {
    }

    public void handleEvent(IEvent event, BusinessDay bd) {

        if (event instanceof ShipDeleteEvent) {
            handleShipDeleteEvent(event, bd);
        } else if (event instanceof DeliveryChangeEvent) {
            handleDeliveryChangeEvent(event, bd);
        } else if (event instanceof PlaceCustomerOrderEvent) {

        } else if (event instanceof ProductionIncreaseEvent) {

        }

    }

    private void handleShipDeleteEvent(IEvent event, BusinessDay bd) {
        ShipService shipService = new ShipService(dataBean);
        ArrivalCalculatorService acs = new ArrivalCalculatorService(shipService, dataBean);
        ShipDeleteEvent shipEvent = (ShipDeleteEvent) event;
        Ship ship = shipEvent.getShip();
        dataBean.getShips().remove(shipEvent.getShip());
        for (LogisticsObject lo : ship.getDeliveries()) {
            LocalDate arrivalDay = dataBean.getBusinessDay(ship.getArrival()).getDate();
            LocalDate recievedDay = acs.addWorkingDays(arrivalDay, Country.GERMANY, 2);
            dataBean.getBusinessDay(recievedDay).getReceivedDeliveries().remove(lo);
            orderService.addToOrder(bd, lo.getComponents());
        }
    }

    private void handleDeliveryChangeEvent(IEvent event, BusinessDay bd) {

        DeliveryChangeEvent deliveryEvent = (DeliveryChangeEvent) event;
        String deliveryID = deliveryEvent.getId();
        Map<Component, Integer> compMap = deliveryEvent.getNewComponents();
        int amount = 0;
        for(Component component : compMap.keySet()){
            amount += compMap.get(component);
        }

        LogisticsObject lo = deliveryService.getDeliveryToDeliveryID(deliveryID);
        Map<Component, Integer> orderMap = new HashMap<>(lo.getComponents());
        lo.setComponents(compMap);
        lo.setSumAmount(amount);

        for (Component c : orderMap.keySet()) {
            orderMap.put(c, orderMap.get(c) - compMap.get(c));
        }

        orderService.addToOrder(bd, orderMap);

    }
}
