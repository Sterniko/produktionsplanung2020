package de.adventureworks.produktionsplanung.controller.production.controller;



import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.services.BusinessCalendar;
import de.adventureworks.produktionsplanung.model.services.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.adventureworks.produktionsplanung.model.services.ProductionService;
import de.adventureworks.produktionsplanung.controller.production.model.ProductionModel;

import java.time.LocalDate;
import java.util.*;


@Controller
public class ProductionController {

    @Autowired
    ProductionService productionService;

    @Autowired
    ProductionModel productionModel;

    @Autowired
    BusinessCalendar businessCalendar;

    @Autowired
    SortService sortService;

    public ProductionController() {

    }

    @RequestMapping("/production")
    public String getCustomers(Model model) {
        this.productionService.calculateRegularProduction();
        List<BusinessDay> businessDayList = new ArrayList<>();
        Map<Country, Boolean> workingDayMap = new HashMap<>();

        //get BusinessDays
        businessDayList = sortService.mapToListBusinessDays(this.productionModel.getBusinessDays());
        //sort them
        businessDayList = sortService.sortBusinessDayList(businessDayList);

        //work with them
        for(BusinessDay bd : businessDayList){


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
