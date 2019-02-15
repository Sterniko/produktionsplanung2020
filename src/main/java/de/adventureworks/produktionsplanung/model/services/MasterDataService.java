package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MasterDataService {

    @Autowired
    private DataBean dataBean;

    public List<String> getMonthList() {
        List<String> months= new ArrayList<>();
        months.add("Januar");
        months.add("Februar");
        months.add("März");
        months.add("April");
        months.add("Mai");
        months.add("Juni");
        months.add("Juli");
        months.add("August");
        months.add("September");
        months.add("Oktober");
        months.add("November");
        months.add("Dezember");
        return months;
    }

    public Map<Bike, Double> convertStringToBikes(Map<String, Double> stringBikeMap) {

        List<Bike> bikes = dataBean.getBikes();
        Map<Bike, Double> bikeMap = new HashMap<>();

        for (String s : stringBikeMap.keySet()) {
            for (Bike bike : bikes) {
                if (bike.getName().equals(s)) {
                    bikeMap.put(bike, stringBikeMap.get(s));
                }
            }
        }

        return bikeMap;
    }

    public Map<Integer, Double> convertMonthsStringToDouble(Map<String, Double> stringMonthMap) {

        Map<Integer, Double> doubleMonthMap = new HashMap<>();

        for (String s : stringMonthMap.keySet()) {
            switch (s) {
                case ("Januar"):
                    doubleMonthMap.put(0, stringMonthMap.get(s));
                    break;
                case ("Februar"):
                    doubleMonthMap.put(1, stringMonthMap.get(s));
                    break;
                case ("März"):
                    doubleMonthMap.put(2, stringMonthMap.get(s));
                    break;
                case ("April"):
                    doubleMonthMap.put(3, stringMonthMap.get(s));
                    break;
                case ("Mai"):
                    doubleMonthMap.put(4, stringMonthMap.get(s));
                    break;
                case ("Juni"):
                    doubleMonthMap.put(5, stringMonthMap.get(s));
                    break;
                case ("Juli"):
                    doubleMonthMap.put(6, stringMonthMap.get(s));
                    break;
                case ("August"):
                    doubleMonthMap.put(7, stringMonthMap.get(s));
                    break;
                case ("September"):
                    doubleMonthMap.put(8, stringMonthMap.get(s));
                    break;
                case ("Oktober"):
                    doubleMonthMap.put(9, stringMonthMap.get(s));
                    break;
                case ("November"):
                    doubleMonthMap.put(10, stringMonthMap.get(s));
                    break;
                case ("Dezember"):
                    doubleMonthMap.put(11, stringMonthMap.get(s));
                    break;
            }
        }
        return doubleMonthMap;
    }

}
