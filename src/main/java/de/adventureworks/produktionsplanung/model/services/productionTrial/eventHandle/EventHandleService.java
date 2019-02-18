package de.adventureworks.produktionsplanung.model.services.productionTrial.eventHandle;


import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import de.adventureworks.produktionsplanung.model.entities.events.*;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import de.adventureworks.produktionsplanung.model.services.*;
import de.adventureworks.produktionsplanung.model.services.productionTrial.ProductionSimulationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        for (Component component : compMap.keySet()) {
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

    public void handleProductionIncreaseEvent(ProductionIncreaseEvent productionIncreaseEvent, BusinessDay changeDate) {

        BusinessWeek bW = productionIncreaseEvent.getBusinessWeek();
        Map<Bike, Integer> increaseAmount = productionIncreaseEvent.getIncreaseAmount();


        boolean isSaturday = isSaturdayWorkingDay(bW);
        int amountOfHolidays = countHolidays(bW);
        int workingDays = 6;
        workingDays -= amountOfHolidays;

        if (isSaturday) {
            workingDays++;
        }

        LocalDate weeksMonday = null;

        //gibt mir den Montag
        for (BusinessDay bD : bW.getDays()) {
            if (bD.getDate().getDayOfWeek() == DayOfWeek.MONDAY) {
                weeksMonday = bD.getDate();
            }
        }
        //

        LocalDate work = weeksMonday;

        //Order increased Production.
        for (Bike b : increaseAmount.keySet()) {
            for (Component c : b.getComponents()) {
                LocalDate mondayMinusLeadTime = weeksMonday.minusDays(c.getSupplier().getLeadTime());
                LocalDate orderDate;
                if (changeDate.getDate().isBefore(mondayMinusLeadTime)) {
                    orderDate = mondayMinusLeadTime;
                } else {
                    orderDate = weeksMonday;
                }

                Map<Component, Integer> orderMap = new HashMap<>();
                orderMap.put(c, increaseAmount.get(b));

                orderService.addToOrder(dataBean.getBusinessDay(orderDate), orderMap);
            }
        }


        Map<LocalDate, Map<Bike, Integer>> dateWithAdditionDisributed = new HashMap();
        //Befüllt die Map mit der Verteilung
        for (int i = 0; i < workingDays; i++) {
            BusinessDay currentBD = dataBean.getBusinessDay(work);
            if (!work.isAfter(changeDate.getDate())) {
                workingDays--;
            } else if (!currentBD.getWorkingDays().get(Country.GERMANY)) {
                dateWithAdditionDisributed.put(work, new HashMap<>());
            }
            work = work.plusDays(1);
        }

        //SONST wird durch null geteilt
        if (workingDays < 1) {
            return;
        }

        for (LocalDate currentDate : dateWithAdditionDisributed.keySet()) {
            for (Map.Entry entry : increaseAmount.entrySet()) {
                int wholeDayAddition = (int) (entry.getValue()) / workingDays;
                Map<Bike, Integer> currentMap = dateWithAdditionDisributed.get(currentDate);
                currentMap.put((Bike) entry.getKey(), wholeDayAddition);
            }
        }
        for (Map.Entry entry : increaseAmount.entrySet()) {
            for (int i = 0; i < (int) (entry.getValue()) % workingDays; i++) {
                List<LocalDate> workingWeek = new ArrayList<>(dateWithAdditionDisributed.keySet());
                LocalDate currentDay = workingWeek.get(i);
                int currentStock = dateWithAdditionDisributed.get(currentDay).get(entry.getKey());
                dateWithAdditionDisributed.get(currentDay).put((Bike) entry.getKey(), currentStock + 1);
            }

        }
        //


        //befüllen des BussniesDays
        for (LocalDate currentDay : dateWithAdditionDisributed.keySet()) {
            BusinessDay currentBD = dataBean.getBusinessDay(currentDay);
            Map<Bike, Integer> newtoAdd = dateWithAdditionDisributed.get(currentDay);
            currentBD.setAdditionalProduction(ProductionSimulationUtil.addMaps(newtoAdd, currentBD.getAdditionalProduction()));
        }
    }

    public void handleCustomerOrderEvent(CustomerOrderEvent customerOrderEvent, BusinessDay changeDate) {
        LocalDate latestPossibleSentDate = customerOrderEvent.getDueProductionDate();
        LocalDate today = changeDate.getDate();
        boolean isPrio = customerOrderEvent.isPrio();
        Map<Bike, Integer> bikesToAdd = customerOrderEvent.getOrderAmount();
        int workDaysAmount = 0;
        int allDays = 0;
        int bikesToAddAmount=ProductionSimulationUtil.countBikes(bikesToAdd);
        List<LocalDate> workingDays= new ArrayList<>();

        LocalDate tempDate = today;
        // counts workingDays and allDays
        while (tempDate.isEqual(latestPossibleSentDate) || tempDate.isBefore(latestPossibleSentDate)) {
            if (tempDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
                if (isSaturdayWorkingDay(dataBean.getBusinessDay(tempDate))) {
                    workDaysAmount++;
                    workingDays.add(tempDate);
                }
            } else if (dataBean.getBusinessDay(tempDate).getWorkingDays().get(Country.GERMANY)) {
                workDaysAmount++;
                workingDays.add(tempDate);
            }
            tempDate = tempDate.plusDays(1);
            allDays++;
        }

        if(workDaysAmount==0){return;}


        tempDate = today;
        for(int i = 0; i<workDaysAmount; i++){
            int addAmount= bikesToAddAmount/workDaysAmount;
            BusinessDay currentBD = dataBean.getBusinessDay(workingDays.get(i));
            if(isPrio){
                //TODO hier aus der Map was ins untere einfügen
                currentBD.getPrioProduction();
            }else{
                //TODO hier aus der Map was ins untere einfügen
                currentBD.getAdditionalProduction();
            }
        }


        tempDate = today;
        for(int i = 0; i<bikesToAddAmount%workDaysAmount; i++){
            int addAmount= bikesToAddAmount%workDaysAmount;
            BusinessDay currentBD = dataBean.getBusinessDay(workingDays.get(i));
            if(isPrio){
                //TODO hier aus der Map was ins untere einfügen
                currentBD.getPrioProduction();
            }else{
                //TODO hier aus der Map was ins untere einfügen
                currentBD.getAdditionalProduction();
            }
        }

    }




/*    public BusinessDay getNextWorkingDay(LocalDate date){
        BusinessDay currentBD=dataBean.getBusinessDay(date);
        if(date.getDayOfWeek()==DayOfWeek.SATURDAY){
            if(isSaturdayWorkingDay(dataBean.getBusinessDay(date))){

            }
        }
        if(currentBD.getWorkingDays().get(Country.GERMANY)){
            return getNextWorkingDay(date.plusDays(1));
        }

    }*/


    public static boolean isSaturdayWorkingDay(BusinessDay bd) {
        if (bd.getDate().getDayOfWeek() == DayOfWeek.SATURDAY) {
            for (Integer entry : bd.getPlannedProduction().values()) {
                if (entry != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSaturdayWorkingDay(BusinessWeek bW) {
        for (BusinessDay bd : bW.getDays()) {
            if (bd.getDate().getDayOfWeek() == DayOfWeek.SATURDAY) {
                for (Integer entry : bd.getPlannedProduction().values()) {
                    if (entry != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int countHolidays(BusinessWeek bW) {
        int count = 0;
        for (BusinessDay bd : bW.getDays()) {
            for (Map.Entry entry : bd.getWorkingDays().entrySet()) {
                if (entry.getKey() == Country.GERMANY) {
                    if ((boolean) entry.getValue()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
