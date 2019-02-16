package de.adventureworks.produktionsplanung.model.services.productionTrial;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import de.adventureworks.produktionsplanung.model.services.BusinessCalendar;
import de.adventureworks.produktionsplanung.model.services.DataInitService;
import de.adventureworks.produktionsplanung.model.services.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = {DataBean.class, DataInitService.class, ProductionService2.class, OrderService.class, BusinessCalendar.class})
public class ProductionService2Test {

    @Autowired
    private DataBean dataBean;

    @Autowired
    private ProductionService2 productionService2;

    private Map<Bike, Double> relativeBikeProduction;

    private double[] monthPercentArr;

    private int yearlyProduction;


    @Test
    public void testProductionForDay() {
        productionService2.simulateInitialProduction( 2019);

        List<LocalDate> sortedDays = new ArrayList<LocalDate>(dataBean.getBusinessDays().keySet());
        Collections.sort(sortedDays);

        int absoultePlannedP = 0;
        int absoluteActualP = 0;

        for (LocalDate date: sortedDays) {
            BusinessDay businessDay = dataBean.getBusinessDay(date);
            System.out.print(businessDay.getDate());
            System.out.print(": ");
            for (LogisticsObject l : businessDay.getReceivedDeliveries()) {
                System.out.print(l.getSupplier().getName());
                System.out.print(": ");
                int sum = 0;
                for (Component c: l.getComponents().keySet()) {
                    sum += l.getComponents().get(c);
                }
                System.out.print(sum);
                System.out.print(", ");

            }
            System.out.println();
            int plannedProdsum = 0;
            for (Bike b: businessDay.getPlannedProduction().keySet()) {
                plannedProdsum+= businessDay.getPlannedProduction().get(b);
            }
            System.out.println("plannedP: " + plannedProdsum);
            absoultePlannedP += plannedProdsum;
            int prodsum = 0;
            for (Bike b: businessDay.getActualProduction().keySet()) {
                prodsum+= businessDay.getActualProduction().get(b);
            }
            System.out.println("actualP: " + prodsum);
            absoluteActualP += prodsum;

            if (plannedProdsum != prodsum) {
                System.out.println("############################################BINBEHINDERTSCHWULUNDSINGLE###########################################################");
            }

            int warehouseSum = 0;

            for (Component c: businessDay.getWarehouseStock().keySet()) {
                warehouseSum+= businessDay.getWarehouseStock().get(c);
            }
            System.out.println("warehouse: " + warehouseSum);

        }

        System.out.println("ABSOLUTE PLANNED:" + absoultePlannedP);
        System.out.println("ABSOLUTE ACTUAL:" + absoluteActualP);

        int sumProd = 0;
        for (LocalDate date: dataBean.getBusinessDays().keySet()) {
            for(Bike bike: dataBean.getBusinessDays().get(date).getPlannedProduction().keySet()) {
                sumProd += dataBean.getBusinessDays().get(date).getPlannedProduction().get(bike);
            }
        }

        System.out.println("SUM PLANNED" + sumProd);

    }
}