package de.adventureworks.produktionsplanung.model.services;


import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private DataBean dataBean;

    public void placeOrder(BusinessDay bd) {

        ShipService shipService = new ShipService(dataBean);
        ArrivalCalculatorService arrivalCalculatorService = new ArrivalCalculatorService(shipService, dataBean);
        for (Supplier supplier : dataBean.getSuppliers()) {

            Map<Supplier, LogisticsObject> pendingSupplierMap = bd.getPendingSupplierAmount();
            LogisticsObject lo = pendingSupplierMap.get(supplier);
            Map<Component, Integer> componentMap = lo.getComponents();

            int orderedAmount = OrderService.getSumAmount(supplier, bd);
            int supplierLotsize = supplier.getLotSize();


            if ((orderedAmount) >= supplierLotsize) {

                List<LogisticsObject> oldSentList = bd.getSentDeliveries();
                LocalDate arrivalDate = arrivalCalculatorService.calculateDeliveryFrom(bd.getDate(), supplier.getCountry());
                if (supplier.getCountry() == Country.CHINA) {
                    Ship ship = arrivalCalculatorService.getNextAvailabeShip(bd.getDate());
                    ship.addOrder(lo);
                }
                System.out.println(arrivalDate);
                oldSentList.add(lo);
                List<LogisticsObject> oldRecievedList = dataBean.getBusinessDay(arrivalDate).getReceivedDeliveries();
                List<LogisticsObject> newRecievedList = new ArrayList<>();
                for (LogisticsObject logisticsObject : oldRecievedList) {
                    newRecievedList.add(logisticsObject);
                }
                lo.setDepartureDate(bd.getDate());
                lo.setArrivalDate(arrivalDate);
                newRecievedList.add(lo);
                dataBean.getBusinessDay(arrivalDate).setReceivedDeliveries(newRecievedList);

                Map<Component, Integer> newComponentMap = new HashMap<>();

                for (Component c : componentMap.keySet()) {
                    newComponentMap.put(c, 0);
                }
                LogisticsObject logisticsObject = new LogisticsObject();
                logisticsObject.setComponents(newComponentMap);
                logisticsObject.setSumAmount(0);
                pendingSupplierMap.put(supplier, logisticsObject);


            }
        }
    }


    public void addToOrder(BusinessDay bd, Map<Component, Integer> map) {
        for (Component c : map.keySet()) {
            if (map.get(c) != null) {
                if (c.getSupplier().getName().equals("WernerRahmenGMBH")) {
                    int addAmount = map.get(c);
                    OrderService.addAmount(bd, addAmount, c);

                }
                if (c.getSupplier().getName().equals("Tenedores de Zaragoza")) {
                    int addAmount = map.get(c);
                    OrderService.addAmount(bd, addAmount, c);

                }
                if (c.getSupplier().getName().equals("DengwongSaddles")) {
                    int addAmount = map.get(c);
                    OrderService.addAmount(bd, addAmount, c);

                }
            }
        }
    }

    public void addPendingSupplierAmountToDay(Map<Supplier, LogisticsObject> addedPendingSupplierAmount, BusinessDay bd) {


        for (Supplier s : addedPendingSupplierAmount.keySet()) {
            LogisticsObject lo = addedPendingSupplierAmount.get(s);
            Map<Component, Integer> componentMap = lo.getComponents();
            for (Component c : componentMap.keySet()) {
                int amount = componentMap.get(c);
                OrderService.addAmount(bd, amount, c);
            }
            /*
            LocalDate dayBefore = bd.getDate().minusDays(1);
            BusinessDay businessDay = dataBean.getBusinessDay(dayBefore);
            Map<Supplier, LogisticsObject> pendingSupplierAmount = businessDay.getPendingSupplierAmount();

            for (Supplier supplier : pendingSupplierAmount.keySet()) {
                LogisticsObject logisticsObject = pendingSupplierAmount.get(supplier);
                for (Component component : logisticsObject.getComponents().keySet()) {
                    logisticsObject.getComponents().put(component, 0);
                }
            }

            if (dayBefore.isAfter(LocalDate.of(2018, 10, 31))) {
                dataBean.getBusinessDay(dayBefore).setPendingSupplierAmount(pendingSupplierAmount);
            }
            */


        }
    }


    private static int getSumAmount(Supplier supplier, BusinessDay bd) {

        LogisticsObject logisticsObject = bd.getPendingSupplierAmount().get(supplier);
        int sumAmount = logisticsObject.getSumAmount();
        return sumAmount;

    }

    private static void addAmount(BusinessDay bd, int amount, Component component) {

        Map<Supplier, LogisticsObject> pendingSupplierAmount = bd.getPendingSupplierAmount();
        LogisticsObject logisticsObject = pendingSupplierAmount.get(component.getSupplier());
        Map<Component, Integer> componentMap = logisticsObject.getComponents();

        logisticsObject.setSumAmount(logisticsObject.getSumAmount() + amount);
        if (componentMap.containsKey(component)) {
            int oldamount = componentMap.get(component);
            amount += oldamount;
            componentMap.put(component, amount);
        } else {
            componentMap.put(component, amount);
        }
        logisticsObject.setComponents(componentMap);
        pendingSupplierAmount.put(component.getSupplier(), logisticsObject);
        bd.setPendingSupplierAmount(pendingSupplierAmount);
    }


    // Method Stub
    //TODO richtige Methode implementieren

    private static void setDeliveryDate(LogisticsObject logisticsObject) {
        Supplier supplier = logisticsObject.getSupplier();
        int leadTime = supplier.getLeadTime();
        System.out.println(leadTime + " Tage Später wird empfangen");
    }
}






