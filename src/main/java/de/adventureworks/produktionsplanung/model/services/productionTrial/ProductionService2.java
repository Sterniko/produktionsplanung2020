package de.adventureworks.produktionsplanung.model.services.productionTrial;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import de.adventureworks.produktionsplanung.model.entities.events.*;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import de.adventureworks.produktionsplanung.model.services.BusinessCalendar;
import de.adventureworks.produktionsplanung.model.services.OrderService;
import de.adventureworks.produktionsplanung.model.services.productionTrial.eventHandle.DeliveryNotFoundException;
import de.adventureworks.produktionsplanung.model.services.productionTrial.eventHandle.EventHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static de.adventureworks.produktionsplanung.model.services.productionTrial.ProductionSimulationUtil.*;

@Service
public class ProductionService2 {

    @Autowired
    private DataBean dataBean;

    @Autowired
    OrderService orderService;

    @Autowired
    private BusinessCalendar businessCalendar;

    @Autowired
    private EventHandleService eventHandleService;


    public void simulateWholeProduction() {
        simulateWholeProduction(2019);
    }


    public void simulateWholeProduction(int year) {

        int saturdayProdMinBorder = dataBean.getShifts().get(1) * dataBean.getHourlyCapacity();
        int saturdayProdMaxBorder = dataBean.getShifts().get(2) * dataBean.getHourlyCapacity();

        //Jahresproduction auf Monatsproduktion
        Map<Integer, Map<Bike, Integer>> absoluteMonthlyProduction = ProductionInitUtil.getAbsoluteMonthlyProduction(
                dataBean.getBikeProductionShares(), dataBean.getMonthlyProductionShares(), dataBean.getYearlyProduction());

        //Monats- aufTagesproduktion
        Map<LocalDate, Map<Bike, Integer>> dailyProductionForYear =
                ProductionInitUtil.getDailyWorkingDayProductionFromMonthlyProduction(absoluteMonthlyProduction, year, saturdayProdMinBorder, saturdayProdMaxBorder);

        //clear pendingsupplierAmount
        List<LocalDate> dates = new ArrayList<>(dataBean.getBusinessDays().keySet());
        Collections.sort(dates);
        BusinessDay lastDay = null;

        //clear ship deliveries
        for(Ship ship: dataBean.getShips()) {
            ship.setDeliveries(new ArrayList<>());
        }

        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);

        Map<Bike, Integer> emptyProdMap = new HashMap<>();
        for (Bike bike: dataBean.getBikes()) {
            emptyProdMap.put(bike, 0);
        }


        Map<Supplier, LogisticsObject> pendingSupplierAmount = new HashMap<>();
        List<Supplier> supplierList = dataBean.getSuppliers();
        List<Component> componentList;

        for (LocalDate date : dates) {
            for (Supplier supplier : supplierList) {
                componentList = supplier.getComponents();
                LogisticsObject lo = new LogisticsObject();
                Map<Component, Integer> componentIntegerMap = new HashMap<>();
                for(int i = 0; i<componentList.size(); i++){
                    componentIntegerMap.put(componentList.get(i), 0);
                }
                lo.setComponents(new HashMap<>(componentIntegerMap));
                lo.setSupplier(supplier);
                pendingSupplierAmount.put(supplier, lo);
            }
            dataBean.getBusinessDay(date).setPendingSupplierAmount(new HashMap<>(pendingSupplierAmount));
            dataBean.getBusinessDay(date).setActualProduction(new HashMap<>(emptyProdMap));
            dataBean.getBusinessDay(date).setAdditionalProduction(new HashMap<>(emptyProdMap));
            dataBean.getBusinessDay(date).setPrioProduction(new HashMap<>(emptyProdMap));
            dataBean.getBusinessDay(date).setProductionOverhang(new HashMap<>(emptyProdMap));

        }


        //Tagesproduktion als plannedProduktion setzen
        for (LocalDate date : dailyProductionForYear.keySet()) {
            dataBean.getBusinessDay(date).setPlannedProduction(
                    dailyProductionForYear.get(date)
            );
        }


        LocalDate firstDayOfNextYear = LocalDate.of(year + 1, 1, 1);





        //set deliveries
        setDeliveriesFromPlannedProduction(null, firstDayOfYear);


        for (LocalDate date : dates) {
            simulateDay(dataBean.getBusinessDay(date), null);
            //System.out.println(date + ": " + dataBean.getBusinessDay(date).getPlannedProduction());
            lastDay = dataBean.getBusinessDay(date);
        }


        System.out.println("break");

    }

    private void setDeliveriesFromPlannedProduction(LocalDate maximumBackDate, LocalDate startDate) {
        LocalDate firstDayOfNextYear = LocalDate.of(startDate.getYear() + 1, 1, 1);

        //in der ersten Iteration wird nur pendingSupplierAmount am Bestelltag in der Vergangenheit gesetzt. Dieser wird noch nicht auf die nächsten Tage übertragen
        for (LocalDate today = startDate; today.isBefore(firstDayOfNextYear); today = today.plusDays(1)) {

            BusinessDay businessDay = dataBean.getBusinessDay(today);

            Map<Bike, Integer> plannedProduction = businessDay.getPlannedProduction();
            Map<Supplier, Map<Component, Integer>> supplierSortedComponents = ProductionSimulationUtil.transformDailyProductionToComponents(plannedProduction);

            LocalDate earliestPlaceDay = null;

            for (Supplier s : supplierSortedComponents.keySet()) {
                BusinessDay orderBusinessDay;
                LocalDate placeDay;
                if (maximumBackDate == null || today.isAfter(maximumBackDate)) { //regular
                    placeDay = today.minusDays(s.getLeadTime());
                    orderBusinessDay = dataBean.getBusinessDay(placeDay);
                } else {
                    orderBusinessDay = dataBean.getBusinessDay(maximumBackDate);
                    placeDay = maximumBackDate;
                }
                if (earliestPlaceDay == null || placeDay.isBefore(earliestPlaceDay)) {
                    earliestPlaceDay = placeDay;
                }

                System.out.println(orderBusinessDay);

                orderService.addToOrder(orderBusinessDay, supplierSortedComponents.get(s));

                System.out.println("r");
            }
        }

        //Alle Businessdays werden iteriert. Gesendete und empfangene Delivieries werden gelöscht.
        Map<LocalDate, BusinessDay> businessDayMap = dataBean.getBusinessDays();
        List<LocalDate> allSortetLocalDays = new ArrayList<>(businessDayMap.keySet());
        Collections.sort(allSortetLocalDays);
        //init first warehouseStock with 0s.
        Map<Component, Integer> initWarehouseMap = new HashMap<>();
        for (Component c: dataBean.getComponents()) {
            initWarehouseMap.put(c,0);
        }

        for (LocalDate date : allSortetLocalDays) {
            BusinessDay businessDay = dataBean.getBusinessDay(date);
            businessDay.setSentDeliveries(new ArrayList<>());
            businessDay.setReceivedDeliveries(new ArrayList<>());
            businessDay.setWarehouseStock(new HashMap<>(initWarehouseMap));
        }
        Map<Supplier, LogisticsObject> addPendingSupplierMap = new HashMap<>();
        for (LocalDate date : allSortetLocalDays) {
            BusinessDay businessDay = dataBean.getBusinessDay(date);
            orderService.addPendingSupplierAmountToDay(addPendingSupplierMap, businessDay);

            List<IEvent> eventsToBeDeleted = new ArrayList<>();
            for (IEvent event : businessDay.getEventList()) {

                if (event instanceof ShipDeleteEvent) {
                    eventHandleService.handleShipDeleteEvent((ShipDeleteEvent)event, businessDay);
                } else if (event instanceof DeliveryChangeEvent) {
                    try {
                        eventHandleService.handleDeliveryChangeEvent((DeliveryChangeEvent)event, businessDay);
                    } catch (DeliveryNotFoundException e) {
                        eventsToBeDeleted.add(event);
                    }
                } else if (event instanceof ProductionIncreaseEvent) {
                    eventHandleService.handleProductionIncreaseEvent((ProductionIncreaseEvent) event, businessDay);
                } else if (event instanceof  CustomerOrderEvent) {
                    eventHandleService.handleCustomerOrderEvent((CustomerOrderEvent) event, businessDay);
                }
            }
            for (IEvent event: eventsToBeDeleted) {
                businessDay.getEventList().remove(event);
            }

            orderService.placeOrder(businessDay);
            addPendingSupplierMap = businessDay.getPendingSupplierAmount();
        }

    }

    //after Order are set
    private void simulateDay(BusinessDay businessDay, LocalDate maximumBackDate) {

        //Wasserschaden
        for (IEvent event : businessDay.getEventList()) {
            if (event instanceof WarehouseChangeEvent) {
                eventHandleService.handleWarehouseChangeEvent((WarehouseChangeEvent) event, businessDay);
            }
        }

        //Warehouse
        Map<Component, Integer> wareHouseStockAfterDeliveries = addDeliveriesToWarehouseStock(businessDay.getWarehouseStock(), businessDay.getReceivedDeliveries());
        businessDay.setBeforeProductionWarehouseStock(new HashMap<>(wareHouseStockAfterDeliveries));

        BusinessDay nextBusinessDay = dataBean.getBusinessDay(businessDay.getDate().plusDays(1));
        Map<Bike, Integer> actualDailyProduction = new HashMap<>();


        boolean isHoliday =  businessDay.getWorkingDays().get(Country.GERMANY);

        //Production only in days with Businessweek
        if (businessDay.getBusinessWeek() != null  && !isHoliday) {
            List<Integer> shifts = dataBean.getShifts();
            int maxShift = shifts.get(shifts.size() - 1);
            int maxCap;

            boolean weeklyShiftsAlreadySet = maximumBackDate != null && maximumBackDate.isAfter(businessDay.getBusinessWeek().getEarliestWorkingDay().getDate());

            if (weeklyShiftsAlreadySet) {
                maxCap = businessDay.getBusinessWeek().getWorkingHours();
            } else {
                maxCap = dataBean.getHourlyCapacity() * maxShift;
            }

            Map<Bike, Integer> actualPrioProduction = tryToAchieveDailyProduction(businessDay.getPrioProduction(), wareHouseStockAfterDeliveries, maxCap, dataBean.getBikes());
            wareHouseStockAfterDeliveries = substractProductionFromWarehouse(actualPrioProduction, wareHouseStockAfterDeliveries);
            maxCap -= countBikes(actualPrioProduction);

            Map<Bike, Integer> dailyShouldWithoutPrioProduction = addMaps(businessDay.getPlannedProduction(), businessDay.getProductionOverhang());
            dailyShouldWithoutPrioProduction = addMaps(dailyShouldWithoutPrioProduction, businessDay.getAdditionalProduction());
            Map<Bike, Integer> dailyShouldWithPrioProduction = addMaps(dailyShouldWithoutPrioProduction, businessDay.getPrioProduction());
            actualDailyProduction = tryToAchieveDailyProduction(dailyShouldWithoutPrioProduction, wareHouseStockAfterDeliveries, maxCap, dataBean.getBikes());

            actualDailyProduction = addMaps(actualDailyProduction, actualPrioProduction);

            if (!weeklyShiftsAlreadySet) {
                //TODO auslagern
                int neededShift = shifts.get(0);
                double neededCap = countBikes(actualDailyProduction) / dataBean.getHourlyCapacity();
                for (int i = 0; i < shifts.size(); i++) {
                    if (neededCap > shifts.get(i)) {
                        neededShift = shifts.get(Math.min(i +1 , shifts.size() - 1));
                    }
                }

                BusinessWeek businessWeek = businessDay.getBusinessWeek();
                if (businessWeek.getWorkingHours() < neededShift) {
                    businessWeek.setWorkingHours(neededShift);
                }
            }

            businessDay.setActualProduction(actualDailyProduction);


            if (countBikes(dailyShouldWithPrioProduction) != countBikes(actualDailyProduction)) {
                //soll kann nicht erfüllt werden
                if (nextBusinessDay != null) {
                    nextBusinessDay.setProductionOverhang(substractMaps(dailyShouldWithPrioProduction, actualDailyProduction));
                }
            }


        } else {
            if (nextBusinessDay != null) {
                nextBusinessDay.setProductionOverhang((new HashMap<>(businessDay.getProductionOverhang())));
            }

        }

        Map<Component, Integer> newWarehouseStock = substractProductionFromWarehouse(actualDailyProduction, wareHouseStockAfterDeliveries);

        businessDay.setWarehouseStock(newWarehouseStock);
        if (nextBusinessDay != null) {
            nextBusinessDay.setWarehouseStock(new HashMap<>(newWarehouseStock));

        }


    }

}
