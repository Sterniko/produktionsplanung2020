package de.adventureworks.produktionsplanung.model.services.productionTrial;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.services.DataInitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = {DataBean.class, DataInitService.class})
public class ProductionSimulationUtilTest {

    @Autowired
    private DataBean dataBean;

    @Test
    public void simulateInitialProductionTest() {

        Map<Bike, Integer> bikeMap = new HashMap<>();

        Map<Supplier, Map<Component, Integer>> supplierSortedMap = ProductionSimulationUtil.transformDailyProductionToComponents(bikeMap);

        assertEquals(0,supplierSortedMap.size());

        for (Bike bike: dataBean.getBikes()) {
            bikeMap.put(bike, 10);
        }



        supplierSortedMap = ProductionSimulationUtil.transformDailyProductionToComponents(bikeMap);

        int sum = 0;
        for (Supplier s: supplierSortedMap.keySet()) {
            for (Component c: supplierSortedMap.get(s).keySet()) {
                sum += supplierSortedMap.get(s).get(c);
            }
        }

        assertEquals(dataBean.getBikes().size() * 3 * 10, sum);

    }
}