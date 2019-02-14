package de.adventureworks.produktionsplanung.model.services.productionTrial;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import de.adventureworks.produktionsplanung.model.services.BusinessCalendar;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public final class HelperMethods {

    private HelperMethods() {

    }


    static Map<Integer, Map<Bike, Integer>> getAbsoluteMonthlyProduction(Map<Bike, Double> relativeBikeProduction, double[] monthPercentArr, int yearlyProduction) {

        //welches Bike wird im gesamten Jahr wie oft hergestellt
        Map<Bike, Integer> absoluteYearlyBikeProduction = new HashMap<>();


        for (Bike bike : relativeBikeProduction.keySet()) {


            absoluteYearlyBikeProduction.put(bike, (int) Math.round(yearlyProduction * relativeBikeProduction.get(bike)));
        }

        //zusätzlich auf Monate aufgeschlüsselt
        Map<Integer, Map<Bike, Integer>> absoluteProduction = new HashMap<>();

        int monthNumber = 1;
        for (double percentage : monthPercentArr) {//jeder Iterationsschritt ist 1 Monat
            Map<Bike, Integer> monthlyProductionMap = new HashMap<>();

            double overhang = 0;
            for (Bike bike : absoluteYearlyBikeProduction.keySet()) {//jeder Iterationsschritt ist 1 Fahrradproduktion im Monat

                //Verteilung, wenn 0,2 Fahrräder produziert werden sollten, wird der überhnag summiert, bis er 1 ist und dann produziert
                double comaValue = absoluteYearlyBikeProduction.get(bike) * percentage;
                int cutOffValue = (int) comaValue;
                overhang += comaValue - cutOffValue;
                int production = cutOffValue;
                if (overhang >= 1) {
                    production++;
                    overhang--;
                }
                Integer monthlyBikeProductionNumber = production;
                monthlyProductionMap.put(bike, monthlyBikeProductionNumber);

            }

            absoluteProduction.put(monthNumber, monthlyProductionMap);
            monthNumber++;
        }


        return absoluteProduction;
    }


    static Map<LocalDate, Map<Bike, Integer>> getDailyWorkingDayProductionFromMonthlyProduction(Map<Integer, Map<Bike, Integer>> yearlyProduction, int year) {

        BusinessCalendar businessCalendar = new BusinessCalendar();
        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
        LocalDate lastDayOfYear = LocalDate.of(year, 12, 31);

        Map<Integer, List<LocalDate>> workingDaysInMonth = new HashMap<>();

        for (LocalDate date = firstDayOfYear; date.isBefore(lastDayOfYear); date = date.plusDays(1)) {
            if (businessCalendar.isWorkingDay(date)) {
                Integer monthValue = date.getMonthValue();
                if (!workingDaysInMonth.containsKey(monthValue)) {
                    workingDaysInMonth.put(monthValue, new ArrayList<>());
                }
                workingDaysInMonth.get(monthValue).add(date);
            }
        }

        Map<LocalDate, Map<Bike, Integer>> dailyProductionMap = new HashMap<>();

        for (Integer monthValue : workingDaysInMonth.keySet()) {
            int workingDaysNo = workingDaysInMonth.get(monthValue).size();
            Map<Bike, Integer> productionToBeDistributed = yearlyProduction.get(monthValue);

            // initializing Map for every day
            for (LocalDate date : workingDaysInMonth.get(monthValue)) {
                dailyProductionMap.put(date, new HashMap<>());

            }
            //Distribution of monthly to daily production

            Stack<Bike> notEvenlyDistributableStack = new Stack<Bike>();

            for (Bike bike : productionToBeDistributed.keySet()) {
                int prodAmountToBeDistributed = productionToBeDistributed.get(bike);

                int evenlyDistributable = prodAmountToBeDistributed / workingDaysNo;
                int notEvenlyDistributable = prodAmountToBeDistributed % workingDaysNo;

                for (LocalDate date : workingDaysInMonth.get(monthValue)) {
                    dailyProductionMap.get(date).put(bike, evenlyDistributable);
                }

                for (int i = 0; i < notEvenlyDistributable; i++) {
                    notEvenlyDistributableStack.push(bike);
                }

            }

            //Start distribution of not evenly distributabel bikes

            while (!notEvenlyDistributableStack.isEmpty()) {
                for (LocalDate date : workingDaysInMonth.get(monthValue)) {
                    if (notEvenlyDistributableStack.isEmpty()) {
                        break;
                    }
                    Map<Bike, Integer> dailyMapToBeEdited = dailyProductionMap.get(date);
                    Bike bikeToIncrement = notEvenlyDistributableStack.pop();
                    dailyMapToBeEdited.put(bikeToIncrement, dailyMapToBeEdited.get(bikeToIncrement) + 1);
                }

            }

        }

        return dailyProductionMap;

    }

    static List<BusinessWeek> createBusinessWeeksFromWorkingDayProduction(Map<LocalDate, Map<Bike, Integer>> dailyProduction, int year, DataBean dataBean) {

        List<BusinessWeek> businessWeeks = new ArrayList<>();

        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
        LocalDate lastDayOfYear = LocalDate.of(year, 12, 31);

        int weekCounter = 1;
        BusinessWeek businessWeek = new BusinessWeek(weekCounter);

        for (LocalDate date = firstDayOfYear; date.isBefore(lastDayOfYear); date = date.plusDays(1)) {
            businessWeek.getDays().add(dataBean.getBusinessDay(date));
            if (dailyProduction.containsKey(date)) {
                Map<Bike, Integer> plannedWeekProduction = businessWeek.getPlannedProduction();
                for (Bike bike: dailyProduction.get(date).keySet()) {
                    int amountToAdd = dailyProduction.get(date).get(bike);
                    if(! plannedWeekProduction.containsKey(bike)) {
                        plannedWeekProduction.put(bike, amountToAdd);
                    } else {
                        plannedWeekProduction.put(bike, plannedWeekProduction.get(bike) + amountToAdd);
                    }
                }
            }

            if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {//save and init new BusinessWeek
                businessWeeks.add(businessWeek);
                weekCounter++;
                businessWeek = new BusinessWeek(weekCounter);
            }

        }
        if (!businessWeek.getDays().isEmpty()) {
            businessWeeks.add(businessWeek);
        }

        return businessWeeks;
    }
}
