package de.adventureworks.produktionsplanung.controller.production.controller;



import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.services.BusinessCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.adventureworks.produktionsplanung.model.services.ProductionService;
import de.adventureworks.produktionsplanung.controller.production.model.ProductionModel;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class ProductionController {

    @Autowired
    ProductionService productionService;

    @Autowired
    ProductionModel productionModel;

    @Autowired
    BusinessCalendar businessCalendar;

    public ProductionController() {

    }

    @RequestMapping("/production")
    public String getCustomers(Model model) {
        this.productionService.calculateRegularProduction();
        List<BusinessDay> businessDayList = new ArrayList<>();
        Map<Country, Boolean> workingDayMap = new HashMap<>();

        //get BusinessDays
        for(Map.Entry<LocalDate, BusinessDay> entry : this.productionModel.getBusinessDays().entrySet()){
            businessDayList.add(entry.getValue());
        }
        //sort them
        Collections.sort(businessDayList, new Comparator<BusinessDay>() {
                    public int compare(BusinessDay o1, BusinessDay o2) {
                        return o1.getDate().compareTo(o2.getDate());
                    }
        });

        //work with them
        for(BusinessDay bd : businessDayList){
            if(this.businessCalendar.isWorkingDay(bd.getDate())){
                workingDayMap.put(Country.GERMANY, Boolean.TRUE);
            }
            else{
                workingDayMap.put(Country.GERMANY, Boolean.FALSE);
            }
            bd.setWorkingDays(workingDayMap);

            LocalDate date = bd.getDate();
            this.productionService.setProductionForDay(date);
        }
        //After Planned Production is set -> CheckForComponents!
        for(BusinessDay bd2 : businessDayList){
            this.productionService.checkComponentsForDay(bd2);
        }

        model.addAttribute("businessDays",businessDayList);


        return "production";
    }
}
