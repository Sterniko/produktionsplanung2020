package de.adventureworks.produktionsplanung.model.services.productionTrial;


import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.events.IEvent;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import de.adventureworks.produktionsplanung.model.services.BusinessCalendar;
import de.adventureworks.produktionsplanung.model.services.DeliveryService;
import de.adventureworks.produktionsplanung.model.services.OrderService;
import de.adventureworks.produktionsplanung.model.services.init.DataInitService;
import de.adventureworks.produktionsplanung.model.services.productionTrial.eventHandle.EventHandleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataBean.class, DataInitService.class, BusinessCalendar.class, OrderService.class, DeliveryService.class, EventHandleService.class})
public class DeliveryHandlerTest {


    @Autowired
    private DataBean dataBean;

    @Autowired
    private EventHandleService eventHandleService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DataInitService dataInitService;


    @Test
    public void testEventHandling() {

        dataInitService.init();

        List<Component> componentList = dataBean.getComponents();
        Map<Component, Integer> componentIntegerMap2 = new HashMap<>();

        for (Component component : componentList) {
            componentIntegerMap2.put(component, 0);
        }


        LogisticsObject logisticsObject = new LogisticsObject(null, 0, componentIntegerMap2, LocalDate.of(2019, 2, 15), LocalDate.of(2019, 1, 12));
        List<LogisticsObject> logisticsObjectList = new ArrayList<>();

        Map<Component, Integer> componentIntegerMap = new HashMap<>();


        for (Component component : componentList) {
            componentIntegerMap.put(component, 345);
        }
        Supplier supplier = dataBean.getSuppliers().get(0);

        Map<Supplier, LogisticsObject> LOMAP = new HashMap<>();
        LogisticsObject dezemberding = new LogisticsObject();
        dezemberding.setComponents(componentIntegerMap);
        LOMAP.put(supplier, dezemberding);


        dataBean.getBusinessDay(LocalDate.of(2018, 12, 27)).setPendingSupplierAmount(LOMAP);

        logisticsObjectList.add(logisticsObject);
        dataBean.getBusinessDay(LocalDate.of(2019, 2, 15)).setReceivedDeliveries(logisticsObjectList);
        String Id = dataBean.getBusinessDay(LocalDate.of(2019, 2, 15)).getReceivedDeliveries().get(0).getId();
        BusinessDay businessDay = dataBean.getBusinessDay(LocalDate.of(2019, 2, 12));
        Map<Component, Integer> components = dataBean.getBusinessDay(LocalDate.of(2018, 12, 27)).getPendingSupplierAmount().get(supplier).getComponents();

        deliveryService.startEvent(Id, businessDay, components);

        IEvent dce = businessDay.getEventList().get(0);
        //TODO eventHandleService.handleEvent(dce, businessDay);

        System.out.println("i");
    }
}
