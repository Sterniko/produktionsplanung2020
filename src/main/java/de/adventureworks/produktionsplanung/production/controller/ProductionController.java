package de.adventureworks.produktionsplanung.production.controller;



import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.adventureworks.produktionsplanung.model.services.ProductionService;
import de.adventureworks.produktionsplanung.production.model.ProductionModel;

import java.time.LocalDate;
import java.util.*;

@Controller
public class ProductionController {

    ProductionService productionService;
    @Autowired
    ProductionModel productionModel;

    public ProductionController() {

    }

    @RequestMapping("/production")
    public String getCustomers(Model model) {

        this.productionService = new ProductionService(this.productionModel);
        this.productionService.calculateRegularProduction();
        List<BusinessDay> businessDayList = new ArrayList<>();

        for(Map.Entry<LocalDate, BusinessDay> entry : this.productionModel.getBusinessDays().entrySet()){
            businessDayList.add(entry.getValue());
            LocalDate date = entry.getKey();
            this.productionService.setProductionForDay(date);
            this.productionService.checkComponentsForDay(entry.getValue());

        }
        Collections.sort(businessDayList, new Comparator<BusinessDay>() {
                    public int compare(BusinessDay o1, BusinessDay o2) {
                        return o1.getDate().compareTo(o2.getDate());
                    }
        });

        model.addAttribute("businessDays",businessDayList);


        return "production";
    }
}
        /*
        JSONObject jsonDay = new JSONObject();
        JSONObject bikeAmount;
        for(BusinessDay bd : businessDayList){
            bikeAmount = new JSONObject();
            for(Map.Entry<Bike,Integer> entry  : bd.getPlannedProduction().entrySet()){
               bikeAmount.put(entry.getKey().getName() , entry.getValue());
            }
            jsonDay.appendField(bd.getDate().toString(), bikeAmount);
        }*/