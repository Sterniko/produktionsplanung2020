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
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderServiceTest {
    private Supplier china;
    private Supplier germany;
    private Supplier spain;
    private HashMap<Component, Integer> componentMap;
    private HashMap<Component, Integer> componentMap2;
    private Frame frame1;
    private Frame frame2;
    private Fork fork1;
    private Fork fork2;
    private Saddle saddle1;
    private Saddle saddle2;
    private BusinessDay businessDay;
    private BusinessDay businessDay2;
    private HashMap<Supplier, LogisticsObject> lo;
    private HashMap<Supplier, LogisticsObject> lo2;
    private LogisticsObject logisticsObjectS;
    private LogisticsObject logisticsObjectS2;
    private LogisticsObject logisticsObjectG;
    private LogisticsObject logisticsObjectC;
    private ArrayList<LogisticsObject> list;
    private ArrayList<LogisticsObject> list2;
    private HashMap<Component, Integer> map;



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
        businessDay.setPendingSupplierAmount(lo);
        lo.get(spain).setComponents(componentMap);
        list = new ArrayList();
        businessDay.setSentDeliveries(list);
        map = new HashMap<>();
        componentMap2 = new HashMap<>();
        businessDay2 = new BusinessDay();
        HashMap<Supplier, LogisticsObject> lo2 = new HashMap<>();
        LogisticsObject logisticsObjectS2 = new LogisticsObject(spain);
        lo2.put(logisticsObjectS2.getSupplier(), logisticsObjectS2);
        businessDay2.setPendingSupplierAmount(lo2);
        list2 = new ArrayList<>();
        businessDay2.setSentDeliveries(list2);

        }
    @Test
    public void testOrder(){

        map.put(fork1, 100);
        OrderService.addToOrder(businessDay, map);
        map.put(fork1, 20);
        map.put(fork2, 300);

        OrderService.addToOrder(businessDay, map);
        map.put(fork1, 0);
        OrderService.addToOrder(businessDay, map);

        System.out.println(list);
        System.out.println(businessDay.getPendingSupplierAmount());






    }
}
