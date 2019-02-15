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

    @Before
    public void init() {

        relativeBikeProduction= new HashMap<>();

        for (Bike bike : dataBean.getBikes()) {
            switch (bike.getName()) {
                case ("MTBAllrounder"):
                    relativeBikeProduction.put(bike, 0.3);
                    break;
                case ("MTBCompetition"):
                    relativeBikeProduction.put(bike, 0.15);
                    break;
                case ("MTBDownhill"):
                    relativeBikeProduction.put(bike, 0.1);
                    break;
                case ("MTBExtreme"):
                    relativeBikeProduction.put(bike, 0.07);
                    break;
                case ("MTBFreeride"):
                    relativeBikeProduction.put(bike, 0.05);
                    break;
                case ("MTBMarathon"):
                    relativeBikeProduction.put(bike, 0.08);
                    break;
                case ("MTBPerformance"):
                    relativeBikeProduction.put(bike, 0.12);
                    break;
                case ("MTBTrail"):

                    relativeBikeProduction.put(bike, 0.13);
                    break;
            }
        }

        yearlyProduction = 185000;


        monthPercentArr = new double[12];

        this.monthPercentArr[0] = 0.04;
        this.monthPercentArr[1] = 0.06;
        this.monthPercentArr[2] = 0.1;
        this.monthPercentArr[3] = 0.16;
        this.monthPercentArr[4] = 0.14;
        this.monthPercentArr[5] = 0.13;
        this.monthPercentArr[6] = 0.12;
        this.monthPercentArr[7] = 0.09;
        this.monthPercentArr[8] = 0.06;
        this.monthPercentArr[9] = 0.03;
        this.monthPercentArr[10] = 0.04;
        this.monthPercentArr[11] = 0.03;



    }

    @Test
    public void testProductionForDay() {
        productionService2.simulateInitialProduction( 2019);

        List<LocalDate> sortedDays = new ArrayList<LocalDate>(dataBean.getBusinessDays().keySet());
        Collections.sort(sortedDays);
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
        }

        System.out.println("e");

    }
}