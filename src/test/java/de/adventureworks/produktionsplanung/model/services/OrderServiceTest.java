package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.bike.Fork;
import de.adventureworks.produktionsplanung.model.entities.bike.Frame;
import de.adventureworks.produktionsplanung.model.entities.bike.Saddle;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderServiceTest {
    Supplier china;
    Supplier germany;
    Supplier spain;
    HashMap<Component, Integer> componentMap;
    HashMap<Component, Integer> componentMap2;
    Frame frame1;
    Frame frame2;
    Fork fork1;
    Fork fork2;
    Saddle saddle1;
    Saddle saddle2;
    BusinessDay businessDay;
    BusinessDay businessDay2;
    HashMap<Supplier, LogisticsObject> lo;
    HashMap<Supplier, LogisticsObject> lo2;
    LogisticsObject logisticsObjectS;
    LogisticsObject logisticsObjectS2;
    LogisticsObject logisticsObjectG;
    LogisticsObject logisticsObjectC;
    ArrayList<LogisticsObject> list;
    ArrayList<LogisticsObject> list2;



    @Before
    public void init(){
        china = new Supplier("China", 500, 30, Country.CHINA);
        germany = new Supplier("Germany", 10, 30, Country.GERMANY);
        spain = new Supplier("Spain", 50, 30, Country.SPAIN);
        componentMap = new HashMap<>();
        frame1 = new Frame("FX100", germany);
        frame2 = new Frame("FX200", germany);
        fork1 = new Fork("GB1", spain);
        fork2 = new Fork("GB2", spain);
        saddle1 = new Saddle("S1", china);
        saddle2 = new Saddle("S2", china);
        businessDay = new BusinessDay();
        lo = new HashMap<>();
        logisticsObjectS = new LogisticsObject(spain);
        lo.put(logisticsObjectS.getSupplier(), logisticsObjectS);
        logisticsObjectG = new LogisticsObject(germany);
        lo.put(logisticsObjectG.getSupplier(), logisticsObjectG);
        logisticsObjectC = new LogisticsObject(china);
        lo.put(logisticsObjectC.getSupplier(), logisticsObjectC);
        lo.get(spain).setComponents(componentMap);
        logisticsObjectS.setComponents(componentMap);
        businessDay.setPendingSupplierAmount(lo);
        list = new ArrayList();
        businessDay.setSentDeliveries(list);

        componentMap2 = new HashMap<>();
        businessDay2 = new BusinessDay();
        HashMap<Supplier, LogisticsObject> lo2 = new HashMap<>();
        LogisticsObject logisticsObjectS2 = new LogisticsObject(spain);
        lo2.put(logisticsObjectS2.getSupplier(), logisticsObjectS2);
        lo2.get(spain).setComponents(componentMap2);
        businessDay2.setPendingSupplierAmount(lo2);
        list2 = new ArrayList<>();
        businessDay2.setSentDeliveries(list2);

        }
    @Test
    public void testOrder(){
        OrderService.addToOrder(componentMap, fork1, 35);
        OrderService.addToOrder(componentMap, fork2, 20);



        OrderService.placeOrder(spain, componentMap, businessDay);

        System.out.println(list);

        componentMap2.put(fork1, 51);
        componentMap2.put(fork2, 100);

        OrderService.placeOrder(spain, componentMap2, businessDay2);

        System.out.println(list2);





    }
}
