package de.adventureworks.produktionsplanung.model.services.productionTrial;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import de.adventureworks.produktionsplanung.model.services.OrderService;
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

    public void simulateInitialProduction(Map<Bike, Double> relativeBikeProduction, double[] monthPercentArr, int yearlyProduction, int year) {

        Map<Integer, Map<Bike, Integer>> absoluteMonthlyProduction = ProductionInitUtil.getAbsoluteMonthlyProduction(relativeBikeProduction, monthPercentArr, yearlyProduction);
        Map<LocalDate, Map<Bike, Integer>> dailyProductionForYear = ProductionInitUtil.getDailyWorkingDayProductionFromMonthlyProduction(absoluteMonthlyProduction, year);

        for (LocalDate date : dailyProductionForYear.keySet()) {
            dataBean.getBusinessDay(date).setPlannedProduction(
                    dailyProductionForYear.get(date)
            );
        }

        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
        LocalDate firstDayOfNextYear = LocalDate.of(year + 1, 1, 1);

/*
        for (LocalDate date = firstDayOfYear; date.isBefore(firstDayOfNextYear); date = date.plusDays(1)) {
            System.out.println(date);
            calculateProductionForDay(dataBean.getBusinessDay(date), null);
        }
*/

        //set deliveries
        setDeliveriesFromPlannedProduction(null, firstDayOfYear);


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

                OrderService.addToOrder(orderBusinessDay, supplierSortedComponents.get(s));

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
            OrderService.addPendingSupplierAmountToDay(addPendingSupplierMap, businessDay, dataBean);
            OrderService.placeOrder(businessDay, dataBean);
            addPendingSupplierMap = businessDay.getPendingSupplierAmount();
        }

    }

    //after Order are set
    private void simulateDay(BusinessDay businessDay) {
        //TODO Schichten implementieren
        int maxCap = 21 * 65;
        BusinessDay nextBusinessDay = dataBean.getBusinessDay(businessDay.getDate().plusDays(1));


        Map<Component, Integer> wareHouseStockAfterDeliveries = addDeliveriesToWarehouseStock(businessDay.getWarehouseStock(), businessDay.getReceivedDeliveries());

        Map<Bike, Integer> dailyShouldProduction = addMaps(businessDay.getPlannedProduction(), businessDay.getProductionOverhang());

        Map<Bike, Integer> actualDailyProduction = tryToAchieveDailyProduction(dailyShouldProduction, wareHouseStockAfterDeliveries, maxCap);


        if (countBikes(dailyShouldProduction) != countBikes(actualDailyProduction)) {
            //soll kann nicht erfüllt werden
            nextBusinessDay.setProductionOverhang(substractMaps(dailyShouldProduction, actualDailyProduction));
        }

        Map<Component, Integer> newWarehouseStock = substractProductionFromWarehouse(actualDailyProduction, wareHouseStockAfterDeliveries);
        businessDay.setWarehouseStock(newWarehouseStock);
        nextBusinessDay.setWarehouseStock(newWarehouseStock);


    }


}