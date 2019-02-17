package de.adventureworks.produktionsplanung.model.services.productionTrial.eventHandle;


import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import de.adventureworks.produktionsplanung.model.entities.events.DeliveryChangeEvent;
import de.adventureworks.produktionsplanung.model.entities.events.ProductionIncreaseEvent;
import de.adventureworks.produktionsplanung.model.entities.events.ShipDeleteEvent;
import de.adventureworks.produktionsplanung.model.entities.events.WarehouseChangeEvent;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import de.adventureworks.produktionsplanung.model.services.*;
import de.adventureworks.produktionsplanung.model.services.productionTrial.ProductionSimulationUtil;
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

    @Autowired
    private MarketingService marketingService;

   @Autowired
   private ProductionEngagementService productionEngagementService;


    public EventHandleService() {
    }


    public void handleShipDeleteEvent(ShipDeleteEvent event, BusinessDay bd) {

        ShipService shipService = new ShipService(dataBean);
        ArrivalCalculatorService acs = new ArrivalCalculatorService(shipService, dataBean);


        Ship ship = event.getShip();
        dataBean.getShips().remove(event.getShip());
        for (LogisticsObject lo : ship.getDeliveries()) {
            LocalDate arrivalDay = dataBean.getBusinessDay(ship.getArrival()).getDate();
            LocalDate recievedDay = acs.addWorkingDays(arrivalDay, Country.GERMANY, 2);
            dataBean.getBusinessDay(recievedDay).getReceivedDeliveries().remove(lo);
            orderService.addToOrder(bd, lo.getComponents());
        }
    }

    public void handleDeliveryChangeEvent(DeliveryChangeEvent event, BusinessDay bd) throws DeliveryNotFoundException {

        String deliveryID = event.getId();
        Map<Component, Integer> compMap = event.getNewComponents();
        int amount = 0;
        for(Component component : compMap.keySet()){
            amount += compMap.get(component);
        }

        LogisticsObject lo = deliveryService.getDeliveryToDeliveryID(deliveryID);

        if (lo == null) {
            throw new DeliveryNotFoundException(deliveryID);
        }
        Map<Component, Integer> orderMap;
        orderMap = new HashMap<>(lo.getComponents());

        lo.setComponents(compMap);
        lo.setSumAmount(amount);

        for (Component c : orderMap.keySet()) {
            orderMap.put(c, orderMap.get(c) - compMap.get(c));
        }

        orderService.addToOrder(bd, orderMap);

    }

    public void handleWarehouseChangeEvent(WarehouseChangeEvent event, BusinessDay bd) {

        Map<Component, Integer> newWarehouseStock = event.getNewWarehouseStock();
        Map<Component, Integer> oldWarehouseStock = new HashMap<>(bd.getWarehouseStock());
        bd.setWarehouseStock(newWarehouseStock);

        Map<Component, Integer> orderStock = ProductionSimulationUtil.substractMaps(oldWarehouseStock, newWarehouseStock);

        orderService.addToOrder(bd, orderStock);


    }

    public void handleProductionIncreaseEvent(ProductionIncreaseEvent productionIncreaseEvent, BusinessDay bd){

        BusinessWeek bW = productionIncreaseEvent.getBusinessWeek();
        Map<Bike, Integer> increaseAmount = productionIncreaseEvent.getIncreaseAmount();

        Map<Bike, Integer> helperMap = marketingService.getWeeklyPlannedProduction(bd.getDate(), bW);
        Map<Bike, Integer> newWeeklyPlannedProduction = marketingService.addAmountToBusinessWeek(helperMap,increaseAmount);

        productionEngagementService.changeProductionWeek(bd.getDate(),bW,newWeeklyPlannedProduction);
    }
}
