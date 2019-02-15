package de.adventureworks.produktionsplanung.model.services.productionTrial;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.services.BusinessCalendar;
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
@ContextConfiguration (classes = {DataBean.class, DataInitService.class, BusinessCalendar.class})
public class ProductionInitUtilTest {

    @Autowired
    private DataBean dataBean;

    private Map<Bike, Double> relativeBikeProduction;

    private Map<Integer, Double> monthProdDistrib;

    private int yearlyProduction;

    @Before
    public void init() {
        relativeBikeProduction = dataBean.getBikeProductionShares();
        monthProdDistrib = dataBean.getMonthlyProductionShares();
        yearlyProduction = dataBean.getYearlyProduction();
    }


        @Test
        public void TestGetAbsoluteMonthlyProduction () {

            Map<Integer, Map<Bike, Integer>> absoluteProduction = ProductionInitUtil.getAbsoluteMonthlyProduction(relativeBikeProduction, monthProdDistrib, yearlyProduction);
            System.out.println(absoluteProduction);

            int sum = calculateSumOfYearlyProduction(absoluteProduction);
            Assert.assertEquals(sum, yearlyProduction);





        }

        @Test
        public void testGetDailyFromMonthlyProduction() {
            Map<Integer, Map<Bike, Integer>> monthlyProduction = ProductionInitUtil.getAbsoluteMonthlyProduction(relativeBikeProduction, monthProdDistrib, yearlyProduction);

            Map<LocalDate, Map<Bike, Integer>> dailyProduction = ProductionInitUtil.getDailyWorkingDayProductionFromMonthlyProduction(monthlyProduction, 2019);

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

/*
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
*/

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
