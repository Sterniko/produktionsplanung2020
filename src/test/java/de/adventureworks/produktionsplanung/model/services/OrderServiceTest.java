package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataBean.class, DataInitService.class, BusinessCalendar.class})
public class OrderServiceTest {

    @Autowired
    private DataBean dataBean;

    @Test
    public void test() {
        BusinessDay bd = dataBean.getBusinessDay(LocalDate.now());
        OrderService.placeOrder(bd, dataBean);
    }

}