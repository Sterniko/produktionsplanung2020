package de.adventureworks.produktionsplanung.model.services.productionTrial;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import de.adventureworks.produktionsplanung.model.entities.events.*;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import de.adventureworks.produktionsplanung.model.services.BusinessCalendar;
import de.adventureworks.produktionsplanung.model.services.OrderService;
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



    public void handleEvent(IEvent event, BusinessDay bd) {

        if (event instanceof ShipDeleteEvent) {
            eventHandleService.handleShipDeleteEvent(event, bd);
        } else if (event instanceof DeliveryChangeEvent) {
            eventHandleService.handleDeliveryChangeEvent(event, bd);
        } else if (event instanceof PlaceCustomerOrderEvent) {

        } else if (event instanceof ProductionIncreaseEvent) {

        }

    }


    public void simulateWholeProduction(int year) {

        //Jahresproduction auf Monatsproduktion
        Map<Integer, Map<Bike, Integer>> absoluteMonthlyProduction = ProductionInitUtil.getAbsoluteMonthlyProduction(
                dataBean.getBikeProductionShares(), dataBean.getMonthlyProductionShares(), dataBean.getYearlyProduction());

        //Monats- aufTagesproduktion
        Map<LocalDate, Map<Bike, Integer>> dailyProductionForYear = ProductionInitUtil.getDailyWorkingDayProductionFromMonthlyProduction(absoluteMonthlyProduction, year);

        //Tagesproduktion als plannedProduktion setzen
        for (LocalDate date : dailyProductionForYear.keySet()) {
            dataBean.getBusinessDay(date).setPlannedProduction(
                    dailyProductionForYear.get(date)
            );
        }


        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
        LocalDate firstDayOfNextYear = LocalDate.of(year + 1, 1, 1);

        //set deliveries
        setDeliveriesFromPlannedProduction(null, firstDayOfYear);


        List<LocalDate> dates = new ArrayList<>(dataBean.getBusinessDays().keySet());
        Collections.sort(dates);
        for (LocalDate date : dates) {
                simulateDay(dataBean.getBusinessDay(date), null);
                //System.out.println(date + ": " + dataBean.getBusinessDay(date).getPlannedProduction());

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
        for (LocalDate date : allSortetLocalDays) {
            BusinessDay businessDay = dataBean.getBusinessDay(date);
            businessDay.setSentDeliveries(new ArrayList<>());
            businessDay.setReceivedDeliveries(new ArrayList<>());
            businessDay.setWarehouseStock(new HashMap<>());
        }
        Map<Supplier, LogisticsObject> addPendingSupplierMap = new HashMap<>();
        for (LocalDate date : allSortetLocalDays) {
            BusinessDay businessDay = dataBean.getBusinessDay(date);
            orderService.addPendingSupplierAmountToDay(addPendingSupplierMap, businessDay);
            if (businessDay.getDate().isAfter(LocalDate.of(2019, 11, 28))) {
                System.out.println("hi");
            }
            orderService.placeOrder(businessDay);
            addPendingSupplierMap = businessDay.getPendingSupplierAmount();
        }

    }

    //after Order are set
    private void simulateDay(BusinessDay businessDay, LocalDate maximumBackDate) {
        //Warehouse
        Map<Component, Integer> wareHouseStockAfterDeliveries = addDeliveriesToWarehouseStock(businessDay.getWarehouseStock(), businessDay.getReceivedDeliveries());


        BusinessDay nextBusinessDay = dataBean.getBusinessDay(businessDay.getDate().plusDays(1));
        Map<Bike, Integer> actualDailyProduction = new HashMap<>();


        //Production only in days with Businessweek
        if (businessDay.getBusinessWeek() != null) {
            List<Integer> shifts = dataBean.getShifts();
            int maxShift = shifts.get(shifts.size() - 1);
            int maxCap;

            boolean weeklyShiftsAlreadySet = maximumBackDate != null && maximumBackDate.isAfter(businessDay.getBusinessWeek().getEarliestWorkingDay().getDate());

            if (weeklyShiftsAlreadySet) {
                maxCap = businessDay.getBusinessWeek().getWorkingHours();
            } else {
                maxCap = dataBean.getHourlyCapacity() * maxShift;
            }


            Map<Bike, Integer> dailyShouldProduction = addMaps(businessDay.getPlannedProduction(), businessDay.getProductionOverhang());

            //TODO Prio Betsellungen implementieren
            actualDailyProduction = tryToAchieveDailyProduction(dailyShouldProduction, wareHouseStockAfterDeliveries, maxCap);

            if (!weeklyShiftsAlreadySet) {
                //TODO auslagern
                int neededShift = shifts.get(0);
                double neededCap = countBikes(actualDailyProduction) / dataBean.getHourlyCapacity();
                for (int i = 0; i < shifts.size(); i++) {
                    if (neededCap > shifts.get(i)) {
                        neededShift = shifts.get(Math.max(i, shifts.size() - 1));
                    }
                }

                BusinessWeek businessWeek = businessDay.getBusinessWeek();
                if (businessWeek.getWorkingHours() < neededShift) {
                    businessWeek.setWorkingHours(neededShift);
                }
            }

            businessDay.setActualProduction(actualDailyProduction);


            if (countBikes(dailyShouldProduction) != countBikes(actualDailyProduction)) {
                //soll kann nicht erfüllt werden
                if (nextBusinessDay != null) {
                    nextBusinessDay.setProductionOverhang(substractMaps(dailyShouldProduction, actualDailyProduction));
                }
            }


        }

        Map<Component, Integer> newWarehouseStock = substractProductionFromWarehouse(actualDailyProduction, wareHouseStockAfterDeliveries);

        businessDay.setWarehouseStock(newWarehouseStock);
        if (nextBusinessDay != null) {
            nextBusinessDay.setWarehouseStock(new HashMap<>(newWarehouseStock));

        }


    }

}
