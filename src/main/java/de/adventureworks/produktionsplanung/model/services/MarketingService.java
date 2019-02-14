package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class MarketingService {

    public static HashMap<Bike,Integer> getWeeklyPlannedProduction(LocalDate today, BusinessWeek businessWeek) {
        HashMap<Bike, Integer> newWeeklyPlanning = new HashMap<>();
        int weekAllrounder = 0;
        int weekCompetition = 0;
        int weekDownhill = 0;
        int weekExtreme = 0;
        int weekFreeride = 0;
        int weekMarathon = 0;
        int weekPerformance = 0;
        int weekTrail = 0 ;

        //TODO: WOchenplanung holen Für jedes Bike die WochenPlanung speichern

        for (BusinessDay bd : businessWeek.getDays()) {
            for (Map.Entry entry : bd.getPlannedProduction().entrySet()) {
                Bike bike = (Bike) entry.getKey();
                switch (bike.getName()) {
                    case ("MTBAllrounder"):
                        newWeeklyPlanning.put(bike, weekAllrounder += (Integer) entry.getValue());
                        break;
                    case ("MTBCompetition"):
                        newWeeklyPlanning.put(bike, weekCompetition += (Integer) entry.getValue());
                        break;
                    case ("MTBDownhill"):
                        newWeeklyPlanning.put(bike, weekDownhill += (Integer) entry.getValue());
                        break;
                    case ("MTBExtreme"):
                        newWeeklyPlanning.put(bike, weekExtreme += (Integer) entry.getValue());
                        break;
                    case ("MTBFreeride"):
                        newWeeklyPlanning.put(bike, weekFreeride += (Integer) entry.getValue());
                        break;
                    case ("MTBMarathon"):
                        newWeeklyPlanning.put(bike, weekMarathon += (Integer) entry.getValue());
                        break;
                    case ("MTBPerformance"):
                        newWeeklyPlanning.put(bike, weekPerformance += (Integer) entry.getValue());
                        break;
                    case ("MTBTrail"):
                        newWeeklyPlanning.put(bike, weekTrail += (Integer) entry.getValue());
                        break;
                }

            }
        }
        return newWeeklyPlanning;
    }
    public static HashMap<Bike,Integer> addAmountToBusinessWeek(HashMap<Bike,Integer> weeklyPlannig, Map<Bike, Integer> bikesToAdd){
        HashMap<Bike,Integer> newWeekPlan = new HashMap<>();

        //Bikes des Wochenplans durchgehen
        //TODO:Planned Production kein Overhead wie JOJO SAGT !!!
        for(Map.Entry entry : weeklyPlannig.entrySet()){
            Bike bike = (Bike) entry.getKey();

            for(Map.Entry entryToAdd :bikesToAdd.entrySet()){ //Bikes bei der eine erhöhte Nachfrage besteht
                if(entry.getKey().equals(entryToAdd.getKey())){ //Bikes sind dieselben -> Erhöhe um die gewissen Anzahl
                    newWeekPlan.put(bike, (Integer) entry.getValue() + (Integer) entryToAdd.getValue());
                }
            }
        }
        return newWeekPlan;
    }

}
