package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MasterDataService {

    @Autowired
    private DataBean dataBean;

    public List<String> giveMonthList(){
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

    public Map<Bike,Integer> convertStringToBikes(Map<String,Integer> ){

    }

}
