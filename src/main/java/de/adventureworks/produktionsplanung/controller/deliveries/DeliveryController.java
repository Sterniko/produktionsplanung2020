package de.adventureworks.produktionsplanung.controller.deliveries;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.services.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DeliveryController {

    @Autowired
    DataBean dataBean;

    @Autowired
    SortService sortService;

    public DeliveryController(){

    }
    @RequestMapping("/deliveries")
    public String getMarketing(Model model) {
        List<BusinessDay> businessDayList = new ArrayList<>();
        //get BusinessDays
        businessDayList = sortService.mapToListBusinessDays(dataBean.getBusinessDays());
        //sort them
        businessDayList = sortService.sortBusinessDayList(businessDayList);

        model.addAttribute("businessWeeks", dataBean.getBusinessWeeks());
        model.addAttribute("businessDays", businessDayList);


        return "deliveries";
    }
}
