package de.adventureworks.produktionsplanung.model.services.productionTrial;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.*;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.services.BusinessCalendar;
import de.adventureworks.produktionsplanung.model.services.OrderService;
import de.adventureworks.produktionsplanung.model.services.init.DataInitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataBean.class, DataInitService.class, BusinessCalendar.class, OrderService.class})
public class ProductionSimulationUtilTest {

    @Autowired
    private DataBean dataBean;
    Frame frameA;
    Frame frameB;
    Fork forkA;
    Fork forkB;
    Saddle saddleA;
    Saddle saddleB;
    Map<Component, Integer> warehouse;
    Bike bikeA;
    Bike bikeB;


    public ProductionSimulationUtilTest() {
        frameA = new Frame("Frame a", null);
        frameB = new Frame("Frame b", null);
        forkA = new Fork("Fork a", null);
        forkB = new Fork("Fork b", null);
        saddleA = new Saddle("Saddle a", null);
        saddleB = new Saddle("Saddle b", null);
        bikeA = new Bike("BikeA", frameA, forkA, saddleA);
        bikeB = new Bike("BikeB", frameB, forkB, saddleB);
        initWareHouse();
    }


    @Test
    public void simulateInitialProductionTest() {

        Map<Bike, Integer> bikeMap = new HashMap<>();

        Map<Supplier, Map<Component, Integer>> supplierSortedMap = ProductionSimulationUtil.transformDailyProductionToComponents(bikeMap);

        assertEquals(0, supplierSortedMap.size());

        for (Bike bike : dataBean.getBikes()) {
            bikeMap.put(bike, 10);
        }

        supplierSortedMap = ProductionSimulationUtil.transformDailyProductionToComponents(bikeMap);

        int sum = 0;
        for (Supplier s : supplierSortedMap.keySet()) {
            for (Component c : supplierSortedMap.get(s).keySet()) {
                sum += supplierSortedMap.get(s).get(c);
            }
        }

        assertEquals(dataBean.getBikes().size() * 3 * 10, sum);

    }


    @Test
    public void tryToAchieveDailyProductionTest() {
        Map<Bike, Integer> kaufen1 = new HashMap<>();
        Map<Bike, Integer> kaufen2 = new HashMap<>();

        kaufen1.put(bikeA, 10);
        kaufen1.put(bikeB, 50);
        kaufen2.put(bikeA, 5);
        kaufen2.put(bikeB, 5);

        Map<Bike, Integer> ergebnis1 = ProductionSimulationUtil.tryToAchieveDailyProduction(kaufen1, warehouse, 65, dataBean.getBikes());
        Map<Bike, Integer> ergebnis2 = ProductionSimulationUtil.tryToAchieveDailyProduction(kaufen1, warehouse, 55, dataBean.getBikes());
        Map<Bike, Integer> ergebnis3 = ProductionSimulationUtil.tryToAchieveDailyProduction(kaufen2, warehouse, 55, dataBean.getBikes());

        Map<Bike, Integer> expected1 = new HashMap<>();
        Map<Bike, Integer> expected2 = new HashMap<>();
        Map<Bike, Integer> expected3 = new HashMap<>();

        expected1.put(bikeA, 9);
        expected1.put(bikeB, 50);
        expected2.put(bikeA, 5);
        expected2.put(bikeB, 50);
        expected3.put(bikeA, 5);
        expected3.put(bikeB, 5);

        assertEquals(expected1, ergebnis1);
        assertEquals(expected2, ergebnis2);
        assertEquals(expected3, ergebnis3);
    }

    @Test
    public void countingBikesTest() {
        Map<Bike, Integer> kaufen1 = new HashMap<>();
        Map<Bike, Integer> kaufen2 = new HashMap<>();

        kaufen1.put(bikeA, 10);
        kaufen1.put(bikeB, 50);
        kaufen2.put(bikeA, 5);
        kaufen2.put(bikeB, 5);
        kaufen2.put(new Bike(), 0);

        assertEquals(60, ProductionSimulationUtil.countBikes(kaufen1));
        assertEquals(10, ProductionSimulationUtil.countBikes(kaufen2));

    }

    @Test
    public void MapCalcTest() {
        Map<Bike, Integer> kaufen1 = new HashMap<>();
        Map<Bike, Integer> kaufen2 = new HashMap<>();
        Map<Bike, Integer> expected = new HashMap<>();
        Map<Bike, Integer> expected1 = new HashMap<>();
        Bike bikeC = new Bike("bikeC", null, null, null);

        kaufen1.put(bikeA, 10);
        kaufen1.put(bikeB, 50);
        kaufen2.put(bikeA, 5);
        kaufen2.put(bikeB, 5);
        kaufen1.put(bikeC, 30);

        expected.put(bikeA, 5);
        expected.put(bikeB, 45);
        expected.put(bikeC, 30);
        expected1.put(bikeA, 15);
        expected1.put(bikeB, 55);
        expected1.put(bikeC, 30);


        assertEquals(expected1, ProductionSimulationUtil.addMaps(kaufen1, kaufen2));
        assertEquals(expected, ProductionSimulationUtil.substractMaps(kaufen1, kaufen2));
        try {
            ProductionSimulationUtil.substractMaps(kaufen2, kaufen1);
            assertEquals(true, false);
        } catch (IllegalArgumentException e) {
            assertEquals(true, true);
        }

    }


    @Test
    public void substractProductionFromWarehouseTest(){
        Map<Bike, Integer> delete1 = new HashMap<>();
        Map<Bike, Integer> delete2 = new HashMap<>();
        Map<Bike, Integer> delete3 = new HashMap<>();
        Map<Bike, Integer> delete4 = new HashMap<>();

        Bike bikeC = new Bike("bikeC",null,null,null);

        delete1.put(bikeA, 9);
        delete1.put(bikeB, 50);
        delete2.put(bikeA, 10000);
        delete2.put(bikeB, 50);
        delete3.put(bikeA, 10);
        delete3.put(bikeB, 50);
        delete3.put(bikeC,1);

        Map<Component, Integer> excpect1 = new HashMap<>();

        excpect1.put(frameA, 1);
        excpect1.put(forkA, 1);
        excpect1.put(saddleA, 0);
        excpect1.put(frameB, 50);
        excpect1.put(forkB, 50);
        excpect1.put(saddleB, 150);

        assertEquals(excpect1,ProductionSimulationUtil.substractProductionFromWarehouse(delete1,warehouse));
        try {
            ProductionSimulationUtil.substractProductionFromWarehouse(delete2,warehouse);
            assertEquals(true,false);
        }catch(Exception e){
            assertEquals(true,true);
        }
        try {
            ProductionSimulationUtil.substractProductionFromWarehouse(delete3,warehouse);
            assertEquals(true,false);
        }catch(Exception e){
            assertEquals(true,true);
        }



    }

    private void initWareHouse() {
        warehouse = new HashMap<>();
        warehouse.put(frameA, 10);
        warehouse.put(forkA, 10);
        warehouse.put(saddleA, 9);

        warehouse.put(frameB, 100);
        warehouse.put(forkB, 100);
        warehouse.put(saddleB, 200);
    }
}