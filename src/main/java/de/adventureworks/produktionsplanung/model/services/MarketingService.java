package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import de.adventureworks.produktionsplanung.model.entities.events.IEvent;
import de.adventureworks.produktionsplanung.model.entities.events.ProductionIncreaseEvent;
import de.adventureworks.produktionsplanung.model.services.productionTrial.ProductionSimulationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MarketingService {


    public static HashMap<Bike,Integer> getWeeklyPlannedProduction(BusinessWeek businessWeek, boolean planned) {

        HashMap<Bike, Integer> weekPlan = new HashMap<>();
        int weekAllrounder = 0;
        int weekCompetition = 0;
        int weekDownhill = 0;
        int weekExtreme = 0;
        int weekFreeride = 0;
        int weekMarathon = 0;
        int weekPerformance = 0;
        int weekTrail = 0 ;

        for (BusinessDay bd : businessWeek.getDays()) {
            Map<Bike,Integer> production= new HashMap<>();
            if(planned){
                production= bd.getPlannedProduction();
            }else{
                production= bd.getAdditionalProduction();
            }
            for (Map.Entry entry : production.entrySet()) {
                Bike bike = (Bike) entry.getKey();
                switch (bike.getName()) {
                    case ("MTBAllrounder"):
                        weekPlan.put(bike, weekAllrounder += (Integer) entry.getValue());
                        break;
                    case ("MTBCompetition"):
                        weekPlan.put(bike, weekCompetition += (Integer) entry.getValue());
                        break;
                    case ("MTBDownhill"):
                        weekPlan.put(bike, weekDownhill += (Integer) entry.getValue());
                        break;
                    case ("MTBExtreme"):
                        weekPlan.put(bike, weekExtreme += (Integer) entry.getValue());
                        break;
                    case ("MTBFreeride"):
                        weekPlan.put(bike, weekFreeride += (Integer) entry.getValue());
                        break;
                    case ("MTBMarathon"):
                        weekPlan.put(bike, weekMarathon += (Integer) entry.getValue());
                        break;
                    case ("MTBPerformance"):
                        weekPlan.put(bike, weekPerformance += (Integer) entry.getValue());
                        break;
                    case ("MTBTrail"):
                        weekPlan.put(bike, weekTrail += (Integer) entry.getValue());
                        break;
                }

            }
        }
        return weekPlan;
    }





    public static Map<Bike,Integer> addAmountToBusinessWeek(Map<Bike,Integer> weeklyPlanning, Map<Bike, Integer> bikesToAdd){

        Map<Bike,Integer> newWeekPlan = ProductionSimulationUtil.addMaps(weeklyPlanning, bikesToAdd);

        return newWeekPlan;
    }

    public void startEvent(BusinessDay businessDay, Map<Bike, Integer> bikeMap, BusinessWeek bw) {
        ProductionIncreaseEvent productionIncreaseEvent = new ProductionIncreaseEvent(bw, bikeMap);
        List<IEvent> eventList = businessDay.getEventList();
        eventList.add(productionIncreaseEvent);

    }

}
