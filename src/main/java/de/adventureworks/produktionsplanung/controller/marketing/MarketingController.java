package de.adventureworks.produktionsplanung.controller.marketing;


import de.adventureworks.produktionsplanung.controller.sales.SalesRequest;
import de.adventureworks.produktionsplanung.controller.util.RequestMapper;
import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;

import de.adventureworks.produktionsplanung.model.services.MarketingService;
import de.adventureworks.produktionsplanung.model.services.ProductionEngagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MarketingController {


    @Autowired
    private DataBean dataBean;

    @Autowired
    private MarketingService marketingService;

    @Autowired
    private ProductionEngagementService productionEngagementService;

    public MarketingController(){

    }
    @RequestMapping("/marketing")
    public String getMarketing(Model model) {
        model.addAttribute("businessWeeks", dataBean.getBusinessWeeks());
        model.addAttribute("businessDays", dataBean.getBusinessDays());
        model.addAttribute("bikes", dataBean.getBikes());
        model.addAttribute("marketingRequest", new MarketingRequest());
        return "marketing";
    }

    @RequestMapping(value = "/marketing", method = RequestMethod.POST)
    public String postMarketing(MarketingRequest marketingRequest) {

        HashMap<Bike,Integer> helperMap;
        HashMap<Bike,Integer> newWeeklyPlannedProduction;

        //Get Post Data
        LocalDate today = marketingRequest.getPlacementDate();
        int businessWeek = marketingRequest.getBusinessWeek();
        BusinessWeek bW = this.dataBean.getBusinessWeeks().get(businessWeek - 1);
        Map<Bike, Integer> bikeMap = RequestMapper.mapBikeStringMap(marketingRequest.getBikeMap(), dataBean.getBikes());


        helperMap = marketingService.getWeeklyPlannedProduction(today, bW);
        newWeeklyPlannedProduction = marketingService.addAmountToBusinessWeek(helperMap,bikeMap);

        productionEngagementService.changeProductionWeek(today,bW,newWeeklyPlannedProduction);

        return "redirect:/marketing";
    }

}
