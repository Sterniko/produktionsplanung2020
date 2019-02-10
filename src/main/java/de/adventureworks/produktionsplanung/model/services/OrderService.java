package de.adventureworks.produktionsplanung.model.services;


import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    public static void placeOrder(Supplier supplier, Map<Component, Integer> map, BusinessDay bd) {

        if (bd.getPendingSupplierAmount().get(supplier) != null) {

            int lotAmount = OrderService.getSumAmount(supplier, bd);
            int sumAmount = 0;
            int supplierLotsize = supplier.getLotSize();

            for (Component c : map.keySet()) {

                int amount = map.get(c);
                sumAmount += amount;
                OrderService.addAmount(supplier, bd, amount, c);

            }

            if ((sumAmount + lotAmount) >= supplierLotsize) {
                LogisticsObject lo = bd.getPendingSupplierAmount().get(supplier);
                List list = bd.getSentDeliveries();
                list.add(lo);
                bd.setSentDeliveries(list);
                OrderService.setDeliveryDate(lo, bd);
                HashMap<Supplier, LogisticsObject> newMap = new HashMap<>();
                bd.setPendingSupplierAmount(newMap);
            }


        }
    }

    public static void addToOrder(Map<Component, Integer> map, Component c, int amount) {
        if (map.get(c) != null) {
            int oldAmount = map.get(c);
            int newAmount = oldAmount + amount;
            map.put(c, newAmount);
        } else {
            map.put(c, amount);
        }
    }

    private static int getSumAmount(Supplier supplier, BusinessDay bd) {
        if (bd.getPendingSupplierAmount().get(supplier) != null) {
            LogisticsObject logisticsObject = bd.getPendingSupplierAmount().get(supplier);
            int sumAmount = logisticsObject.getSumAmount();
            return sumAmount;
        } else {
            return 0;
        }
    }

    private static void addAmount(Supplier supplier, BusinessDay bd, int amount, Component component) {
        Map<Supplier, LogisticsObject> map = bd.getPendingSupplierAmount();
        LogisticsObject logisticsObject;
        Map<Component, Integer> componentMap;

        if (map.get(supplier) != null) {
            logisticsObject = map.get(supplier);
            componentMap = logisticsObject.getComponents();
        } else {
            logisticsObject = new LogisticsObject(supplier);
            logisticsObject.setSumAmount(0);
            componentMap = new HashMap<>();
            logisticsObject.setComponents(componentMap);
        }

        logisticsObject.setSumAmount(logisticsObject.getSumAmount() + amount);
        componentMap.put(component, amount);
        logisticsObject.setComponents(componentMap);

        map.put(supplier, logisticsObject);

        bd.setPendingSupplierAmount(map);
    }

    private static void setDeliveryDate(LogisticsObject logisticsObject, BusinessDay bd){
        Supplier supplier = logisticsObject.getSupplier();
        int leadTime = supplier.getLeadTime();
        System.out.println(leadTime + " Tage Später wird empfangen");
    }
}






