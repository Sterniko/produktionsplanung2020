package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataBean.class, DataInitService.class, BusinessCalendar.class, OrderService.class})
public class OrderServiceTest {

    @Autowired
    private DataBean dataBean;

    @Autowired
    private OrderService orderService;

    @Test
    public void test() {
        Map<LocalDate, BusinessDay> businessDayMap = dataBean.getBusinessDays();
        BusinessDay bd = businessDayMap.get(LocalDate.now());
        Map<Supplier, LogisticsObject> pendingSupplierMap = bd.getPendingSupplierAmount();
        for (Supplier s : pendingSupplierMap.keySet()) {
            LogisticsObject lo = pendingSupplierMap.get(s);
            Map<Component, Integer> componentMap = lo.getComponents();
            int amount = 0;
            for (Component component : componentMap.keySet()) {
                componentMap.put(component, 500);
                amount += 500;
            }
            lo.setSumAmount(amount);
            lo.setComponents(componentMap);
            pendingSupplierMap.put(s, lo);


        }
        bd.setPendingSupplierAmount(pendingSupplierMap);
        businessDayMap.put(LocalDate.now(), bd);
        dataBean.setBusinessDays(businessDayMap);

        orderService.placeOrder(bd);
        System.out.println("b");
    }
}