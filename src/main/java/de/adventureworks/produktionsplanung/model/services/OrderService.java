package de.adventureworks.produktionsplanung.model.services;


import com.sun.xml.internal.bind.v2.TODO;
import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private DataBean dataBean;

    public static void placeOrder(Supplier supplier, BusinessDay bd, DataBean dataBean) {

        Supplier newSupplier = new Supplier();
        Map<Supplier, LogisticsObject> helpMap = bd.getPendingSupplierAmount();
        for(Supplier s : helpMap.keySet()){
            if(s.getName().equals(supplier.getName())) {
                newSupplier = s;
            }
        }
        supplier =  newSupplier;
        Map<Supplier, LogisticsObject> pendingSupplierMap = bd.getPendingSupplierAmount();
        LogisticsObject lo = pendingSupplierMap.get(supplier);
        Map<Component, Integer> componentMap = lo.getComponents();

        int orderedAmount = OrderService.getSumAmount(supplier, bd);
        int supplierLotsize = supplier.getLotSize();



        if ((orderedAmount) >= supplierLotsize) {
            List list = bd.getSentDeliveries();
            list.add(lo);
            bd.setSentDeliveries(list);
            OrderService.setDeliveryDate(lo, bd);
            for(Component c : componentMap.keySet()){
                componentMap.put(c, 0);
            }
            lo.setSumAmount(0);
            lo.setComponents(componentMap);
            pendingSupplierMap.put(supplier, lo);
            bd.setPendingSupplierAmount(pendingSupplierMap);

            LocalDate deliveryLocalDate = ArrivalCalculater.calculate(bd.getDate(), supplier.getLeadTime(), supplier.getCountry(), dataBean);
            Map<LocalDate, BusinessDay> bdMap = dataBean.getBusinessDays();
            BusinessDay deliveryDate = bdMap.get(deliveryLocalDate);
            List<LogisticsObject> deliverList = deliveryDate.getSentDeliveries();
            List<LogisticsObject> newList = new ArrayList<>();
            for(LogisticsObject logisticsObject : deliverList){
                newList.add(logisticsObject);
            }
            newList.add(lo);
            deliveryDate.setReceivedDeliveries(newList);
            bdMap.put(deliveryLocalDate, deliveryDate);
            dataBean.setBusinessDays(bdMap);

        }


    }


    public static void addToOrder(BusinessDay bd, Map<Component, Integer> map) {
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
        componentMap.put(component, amount);
        logisticsObject.setComponents(componentMap);
        pendingSupplierAmount.put(component.getSupplier(), logisticsObject);
        bd.setPendingSupplierAmount(pendingSupplierAmount);
    }


    // Method Stub
    //TODO richtige Methode implementieren

    private static void setDeliveryDate(LogisticsObject logisticsObject, BusinessDay bd) {
        Supplier supplier = logisticsObject.getSupplier();
        int leadTime = supplier.getLeadTime();
        System.out.println(leadTime + " Tage Später wird empfangen");
    }
}






