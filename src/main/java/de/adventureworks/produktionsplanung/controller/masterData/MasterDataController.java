package de.adventureworks.produktionsplanung.controller.masterData;

import de.adventureworks.produktionsplanung.model.DataBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class MasterDataController {


    @Autowired
    private DataBean dataBean;

    public MasterDataController() {
    }

    @RequestMapping("/masterData")
    public String getMasterData(Model model) {
        model.addAttribute("hourlyCapacity", dataBean.getHourlyCapacity());
        model.addAttribute("yearlyProduction", dataBean.getYearlyProduction());
        model.addAttribute("bikes", dataBean.getBikes());
        model.addAttribute("masterDataRequest", new MasterDataRequest());

        return "masterData";
    }

    @RequestMapping(value = "/masterData", method = RequestMethod.POST)
    public String postMasterData(MasterDataRequest masterDataRequest) {
        Map<String,Double> bikeMap = masterDataRequest.getBikeProductionShares();
        System.out.println(bikeMap);
        int yearlyProduction = masterDataRequest.getYearlyCapacity();
        int hourlyCapacity = masterDataRequest.getHourlyCapacity();

        dataBean.setHourlyCapacity(hourlyCapacity);
        dataBean.setYearlyProduction(yearlyProduction);


        return "redirect:/masterData";
    }


}
