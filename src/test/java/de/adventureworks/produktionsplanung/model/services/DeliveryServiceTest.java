package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import de.adventureworks.produktionsplanung.model.services.init.DataInitService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataBean.class, DataInitService.class, BusinessCalendar.class, DeliveryService.class, SortService.class})

public class DeliveryServiceTest {
    @Autowired
    private DataBean dataBean;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private SortService sortService;

    BusinessDay departureBD;
    BusinessDay arrivalBD;
    String deliveryID;
    HashMap<Component,Integer> compMap;
    @Before
    public void init(){
        compMap = new HashMap<>();

        for(Component c : dataBean.getComponents()){
            compMap.put(c,10);
        }
        deliveryID = "Hallo";
        LogisticsObject lo = new LogisticsObject();
        lo.setSumAmount(140);
        lo.setDepartureDate(LocalDate.now());
        lo.setArrivalDate(LocalDate.now().plusDays(9));
        lo.setId(deliveryID);

        departureBD = dataBean.getBusinessDay(LocalDate.now());
        arrivalBD = dataBean.getBusinessDay(LocalDate.now().plusDays(9));
        List<LogisticsObject> listLo = new ArrayList<>();

        listLo.add(lo);

        departureBD.setSentDeliveries(listLo);
        arrivalBD.setReceivedDeliveries(listLo);
    }

    @Test
    public void testGetBusinessDayToDeliveryID() {
        /*
        List<BusinessDay> businessDays = deliveryService.getBusinessDayToDeliveryID(deliveryID);
        businessDays = sortService.sortBusinessDayList(businessDays);
        Assert.assertEquals(businessDays.size(), 2);
        Assert.assertEquals(businessDays.get(0), departureBD);
        Assert.assertEquals(businessDays.get(1), arrivalBD);
        */

    }

    @Test
    public void printTest() {
        LocalDate date = LocalDate.of(2019, 1, 1);
        for (int i = 1; i<366; i++){
            System.out.print("t" + i + " = \"" + date + "\";");
        date = date.plusDays(1);
    }

        System.out.println();
        for (int i = 1; i < 366; i++) {
            System.out.print("t"+i+","  );
        }
        System.out.println();
        for (int i = 1; i < 366; i++) {
            System.out.print(Math.random() + "," );
        }


    }

    @Test
    public void printValues() {
        System.out.println();
        for (int i = 1; i < 366; i++) {
            System.out.print("var p" + i + " = " + "/*[[${p" + i + "}]]*/2;");
        }
        System.out.println();
        for (int i = 1; i < 366; i++) {
            System.out.print("p" + i + "," );
        }
        System.out.println();

        for (int i = 1; i < 366; i++) {
            System.out.print("var c" + i + " = " + "/*[[${c" + i + "}]]*/2;");
        }
        System.out.println();
        for (int i = 1; i < 366; i++) {
            System.out.print("c" + i + "," );
        }

        System.out.println();

        for (int i = 1; i < 366; i++) {
            System.out.print("var o" + i + " = " + "/*[[${o" + i + "}]]*/2;");
        }
        System.out.println();
        for (int i = 1; i < 366; i++) {
            System.out.print("o" + i + "," );
        }

        System.out.println();

        for (int i = 1; i < 366; i++) {
            System.out.print("var a" + i + " = " + "/*[[${a" + i + "}]]*/2;");
        }
        System.out.println();
        for (int i = 1; i < 366; i++) {
            System.out.print("a" + i + "," );
        }



        System.out.println();
    }

    @Test
    public void testSetNewDelivery(){


        /*

        HashMap<Component,Integer> helperMap = new HashMap<>();

        for(Component c : this.dataBean.getComponents()){
            helperMap.put(c, 20);
        }

        List<BusinessDay> businessDays = deliveryService.getBusinessDayToDeliveryID(deliveryID);

        businessDays = sortService.sortBusinessDayList(businessDays);

        Assert.assertEquals(arrivalBD.getReceivedDeliveries().get(0).getSumAmount(),140);
        Assert.assertEquals(departureBD.getSentDeliveries().get(0).getSumAmount(),140);

        if((businessDays.size() > 1)){
            List<LogisticsObject> sentDeliveries = departureBD.getSentDeliveries();
            List<LogisticsObject> receivedDeliveries = arrivalBD.getReceivedDeliveries();
            deliveryService.setNewDelivery(sentDeliveries,deliveryID,helperMap);
            deliveryService.setNewDelivery(receivedDeliveries,deliveryID,helperMap);
        }
        Assert.assertEquals(arrivalBD.getReceivedDeliveries().get(0).getId(), deliveryID );
        Assert.assertEquals(arrivalBD.getReceivedDeliveries().get(0).getSumAmount(),280);
        Assert.assertEquals(departureBD.getSentDeliveries().get(0).getId(),deliveryID);
        Assert.assertEquals(departureBD.getSentDeliveries().get(0).getSumAmount(),280);

        LogisticsObject loArrival = arrivalBD.getReceivedDeliveries().get(0);
        for(Map.Entry entryArrival : loArrival.getComponents().entrySet()){
                Assert.assertEquals(entryArrival.getValue(),20);
        }

        LogisticsObject loDeparture = departureBD.getSentDeliveries().get(0);

        for(Map.Entry entryDeparture : loDeparture.getComponents().entrySet()){
                Assert.assertEquals(entryDeparture.getValue(),20);

        }

        */


    }
}

