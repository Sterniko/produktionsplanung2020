package de.adventureworks.produktionsplanung.model.services.productionTrial;

import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.services.BusinessCalendar;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public final class ProductionInitUtil {

    private ProductionInitUtil() {

    }


    static Map<Integer, Map<Bike, Integer>> getAbsoluteMonthlyProduction(Map<Bike, Double> relativeBikeProduction, Map<Integer, Double> monthlyProductionShares, int yearlyProduction) {

        //welches Bike wird im gesamten Jahr wie oft hergestellt
        Map<Bike, Integer> absoluteYearlyBikeProduction = new HashMap<>();


        for (Bike bike : relativeBikeProduction.keySet()) {

            absoluteYearlyBikeProduction.put(bike, (int) Math.round(yearlyProduction * relativeBikeProduction.get(bike)));
        }

        //zusätzlich auf Monate aufgeschlüsselt
        Map<Integer, Map<Bike, Integer>> absoluteProduction = new HashMap<>();

        for (int monthNumber = 1; monthNumber < 13; monthNumber++) {//jeder Iterationsschritt ist 1 Monat
            Map<Bike, Integer> monthlyProductionMap = new HashMap<>();
            double percentage = monthlyProductionShares.get(monthNumber);

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
        }


        return absoluteProduction;
    }


    static Map<LocalDate, Map<Bike, Integer>> getDailyWorkingDayProductionFromMonthlyProduction(Map<Integer, Map<Bike, Integer>> yearlyProduction, int year, int saturdayDailyProdBorder, int maxProdBorder) {

        BusinessCalendar businessCalendar = new BusinessCalendar();
        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
        LocalDate firstDayOfNextYear = LocalDate.of(year + 1, 1, 1);

        Map<Integer, Boolean> monthSaturdayAllowedMap = new HashMap<>();
        //Wenn die Tagproduktion ohne samstage über der borderprod liegt, wird samstagsarbeit erlaubt.
        for (int i: yearlyProduction.keySet()) {
            int sum = 0;
            for (Bike bike: yearlyProduction.get(i).keySet()) {
                sum += yearlyProduction.get(i).get(bike);
            }
            int workingDaysWithSaturday = 0;
            int workingDaysWithoutSaturday = 0;
            LocalDate date = LocalDate.of(year, i, 1);
            while(date.getMonth().getValue() == i) {
                if (! (businessCalendar.isHoliday(date) || date.getDayOfWeek()== DayOfWeek.SUNDAY) ){
                    workingDaysWithSaturday++;
                }
                if(! (businessCalendar.isHoliday(date) || date.getDayOfWeek()== DayOfWeek.SUNDAY || date.getDayOfWeek()== DayOfWeek.SATURDAY )) {
                    workingDaysWithoutSaturday++;
                }
                date = date.plusDays(1);
            }

            //int daysWithoutSaturday = businessCalendar.getWorkingDaysOutOfMonthAndYear(i, 2019);



            int dailyProductionInMonthWithoutSaturday = sum / workingDaysWithoutSaturday;
            int dailyProductionWithSaturday = sum / workingDaysWithSaturday;

            boolean saturdayWorkAllowed  = dailyProductionInMonthWithoutSaturday > maxProdBorder// Ohne Samstage wir dmehr als 21 Stunden/Tag produziert
                    || (dailyProductionInMonthWithoutSaturday > saturdayDailyProdBorder && dailyProductionWithSaturday <= saturdayDailyProdBorder );

            monthSaturdayAllowedMap.put(i,saturdayWorkAllowed);
        }


        Map<Integer, List<LocalDate>> workingDaysInMonth = new HashMap<>();
        List<LocalDate> offDays = new ArrayList<>();

        for (LocalDate date = firstDayOfYear; date.isBefore(firstDayOfNextYear); date = date.plusDays(1)) {
            Integer monthValue = date.getMonthValue();
            boolean saturdayWorkAllowed = monthSaturdayAllowedMap.get(monthValue);
            if (businessCalendar.isWorkingDay(date) || (saturdayWorkAllowed && businessCalendar.isSaturday(date) && (!businessCalendar.isHoliday(date)))) {
                if (!workingDaysInMonth.containsKey(monthValue)) {
                    workingDaysInMonth.put(monthValue, new ArrayList<>());
                }
                workingDaysInMonth.get(monthValue).add(date);
            } else {
                offDays.add(date);
            }
        }

        Map<LocalDate, Map<Bike, Integer>> dailyProductionMap = new HashMap<>();

        for (Integer monthValue : workingDaysInMonth.keySet()) {
            int workingDaysNo = workingDaysInMonth.get(monthValue).size();
            Map<Bike, Integer> productionToBeDistributed = yearlyProduction.get(monthValue);

            // initializing Map for every working day
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

        Set<Bike> bikesToBeProduced = yearlyProduction.get(1).keySet();
        Map<Bike, Integer> emptyBikeProdMap = new HashMap<>();
        for (Bike bike : bikesToBeProduced) {
            emptyBikeProdMap.put(bike, 0);
        }

        for (LocalDate date : offDays) {
            dailyProductionMap.put(date, new HashMap<>(emptyBikeProdMap));
        }

        return dailyProductionMap;

    }

/*    static List<BusinessWeek> createBusinessWeeksFromWorkingDayProduction(Map<LocalDate, Map<Bike, Integer>> dailyProduction, int year, DataBean dataBean) {

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
                businessWeek = new Bus575inessWeek(weekCounter);
            }

        }
        if (!businessWeek.getDays().isEmpty()) {
            businessWeeks.add(businessWeek);
        }

        return businessWeeks;
    }*/
}
