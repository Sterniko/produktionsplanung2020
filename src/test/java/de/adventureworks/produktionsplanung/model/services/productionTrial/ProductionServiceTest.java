package de.adventureworks.produktionsplanung.model.services.productionTrial;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.services.DataInitService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = {DataBean.class, DataInitService.class, ProductionService2.class})
public class ProductionServiceTest {

    @Autowired
    private DataBean dataBean;

    @Autowired
    ProductionService2 productionService2;

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
    public void testInitialPlanningProduction() {
        productionService2.simulateInitialProduction(relativeBikeProduction, monthPercentArr, yearlyProduction, 2019);

        Map<LocalDate, BusinessDay> businessDays = dataBean.getBusinessDays();
        System.out.println("stop");
    }

}
