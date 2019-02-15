package de.adventureworks.produktionsplanung.model.services.productionTrial;

import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public final class HelperMethods {

    private HelperMethods() {

    }


    static Map<Integer, Map<Bike, Integer>> getAbsoluteMonthlyProduction(Map<Bike, Double> relativeBikeProduction, double[] monthPercentArr, int yearlyProduction) {

        //welches Bike wird im gesamten Jahr wie oft hergestellt
        Map<Bike, Integer> absoluteYearlyBikeProduction = new HashMap<>();

        for (Bike bike: relativeBikeProduction.keySet()) {
            Integer monthlyProduction = (int) Math.round(yearlyProduction * relativeBikeProduction.get(bike));
            absoluteYearlyBikeProduction.put(bike, monthlyProduction);
        }

        //zusätzlich auf Monate aufgeschlüsselt
        Map<Integer, Map<Bike, Integer>> absoluteProduction = new HashMap<>();

        int monthNumber = 1;
        for (double percentage: monthPercentArr) {//jeder Iterationsschritt ist 1 Monat
            Map<Bike, Integer> monthlyProductionMap = new HashMap<>();

            for (Bike bike: absoluteYearlyBikeProduction.keySet()) {//jeder Iterationsschritt ist 1 Fahrradproduktion im Monat

                Integer monthlyBikeProductionNumber = (int) Math.round(absoluteYearlyBikeProduction.get(bike) * percentage);
                monthlyProductionMap.put(bike, monthlyBikeProductionNumber);

            }

            absoluteProduction.put(monthNumber, monthlyProductionMap);
            monthNumber++;
        }

        //TODO: Rundungsfehler entfernen?

        return absoluteProduction;
    }



    static List<BusinessWeek> transformMonthlyToWeeklyProduction(List<BusinessWeek> businessWeeks, Map<Integer, Map<Bike, Integer>> yearlyProductionNumbers) {


        List<BusinessDay> workingDays = new ArrayList<>();
        return null;


    }
}
