package de.adventureworks.produktionsplanung.model.services.productionTrial;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import de.adventureworks.produktionsplanung.model.services.DataInitService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.*;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration (classes = {DataBean.class, DataInitService.class})
public class HelperMethodsTest {

    @Autowired
    private DataBean dataBean;

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
        public void TestGetAbsoluteMonthlyProduction () {

            Map<Integer, Map<Bike, Integer>> absoluteProduction = HelperMethods.getAbsoluteMonthlyProduction(relativeBikeProduction, monthPercentArr, yearlyProduction);
            System.out.println(absoluteProduction);

            int sum = calculateSumOfYearlyProduction(absoluteProduction);
            Assert.assertEquals(sum, yearlyProduction);





        }

        @Test
        public void testGetDailyFromMonthlyProduction() {
            Map<Integer, Map<Bike, Integer>> monthlyProduction = HelperMethods.getAbsoluteMonthlyProduction(relativeBikeProduction, monthPercentArr, yearlyProduction);

            Map<LocalDate, Map<Bike, Integer>> dailyProduction = HelperMethods.getDailyWorkingDayProductionFromMonthlyProduction(monthlyProduction, 2019);

            System.out.println(dailyProduction);

            int sum = 0;
            for (LocalDate date: dailyProduction.keySet()) {
                for (Bike bike: dailyProduction.get(date).keySet()) {
                    sum += dailyProduction.get(date).get(bike);
                }
            }

            Assert.assertEquals(yearlyProduction, sum);


            List<LocalDate> sortedLocalDates = new ArrayList<>(dailyProduction.keySet());
            Collections.sort(sortedLocalDates);
            for (LocalDate date : sortedLocalDates) {
                int dailySum = 0;
                System.out.print(date);
                System.out.print(": ");
                for (Bike bike: dailyProduction.get(date).keySet()) {
                    System.out.print(bike.getName());
                    System.out.print(":");
                    System.out.print(dailyProduction.get(date).get(bike));
                    dailySum += dailyProduction.get(date).get(bike);
                    System.out.print(", ");
                }
                System.out.print("   dailySum = " + dailySum);
                System.out.println();

            }

        }

        @Test
        public void testBusinessWeeks() {

            Map<Integer, Map<Bike, Integer>> monthlyProduction = HelperMethods.getAbsoluteMonthlyProduction(relativeBikeProduction, monthPercentArr, yearlyProduction);
            Map<LocalDate, Map<Bike, Integer>> dailyProduction = HelperMethods.getDailyWorkingDayProductionFromMonthlyProduction(monthlyProduction, 2019);
            List<BusinessWeek> businessWeeks = HelperMethods.createBusinessWeeksFromWorkingDayProduction(dailyProduction, 2019, dataBean);

            for (BusinessWeek week : businessWeeks) {
                int weeklySum = 0;
                System.out.print(week.getCalendarWeek());
                System.out.print(": ");
                for (Bike bike: week.getPlannedProduction().keySet()) {
                    System.out.print(bike.getName());
                    System.out.print(":");
                    int plannedProduction = 0;
                    if (week.getPlannedProduction().containsKey(bike)) {
                        plannedProduction = week.getPlannedProduction().get(bike);
                    }
                    System.out.print(plannedProduction);
                    weeklySum += plannedProduction;
                    System.out.print(", ");

                }

                System.out.print("   weeklySum = " + weeklySum);
                System.out.println();

            }

        }

        private static int calculateSumOfYearlyProduction(Map<Integer, Map<Bike, Integer>> yearlyProduction) {

            int sum = 0;
            for (Integer month: yearlyProduction.keySet()) {
                for (Bike bike: yearlyProduction.get(month).keySet()) {
                    sum += yearlyProduction.get(month).get(bike);
                }
            }
            return sum;


        }

    }
