package de.adventureworks.produktionsplanung.controller.masterData;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.services.MasterDataService;
import de.adventureworks.produktionsplanung.model.services.productionTrial.ProductionService2;
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

    @Autowired
    private MasterDataService masterDataService;

    @Autowired
    private ProductionService2 productionService2;

    public MasterDataController() {
    }

    @RequestMapping("/masterData")
    public String getMasterData(Model model) {
        model.addAttribute("hourlyCapacity", dataBean.getHourlyCapacity());
        model.addAttribute("yearlyProduction", dataBean.getYearlyProduction());
        model.addAttribute("bikes", dataBean.getBikes());
        model.addAttribute("masterDataRequest", new MasterDataRequest());
        model.addAttribute("months", masterDataService.getMonthList());

        return "masterData";
    }

    @RequestMapping(value = "/postProductionValues", method = RequestMethod.POST)
    public String postProductionValues(MasterDataRequest masterDataRequest) {

        int yearlyProduction = masterDataRequest.getYearlyCapacity();
        int hourlyCapacity = masterDataRequest.getHourlyCapacity();

        dataBean.setHourlyCapacity(hourlyCapacity);
        dataBean.setYearlyProduction(yearlyProduction);

        productionService2.simulateWholeProduction();

        return "redirect:/masterData";
    }

    @RequestMapping(value = "/postBikeValues", method = RequestMethod.POST)
    public String postBikeValues(MasterDataRequest masterDataRequest) {

        Map<String, Double> stringBikeMap = masterDataRequest.getBikeProductionShares();
        Map<Bike, Double> bikeMap = masterDataService.convertStringToBikes(stringBikeMap);
        dataBean.setBikeProductionShares(bikeMap);

        return "redirect:/masterData";
    }

    @RequestMapping(value = "/postMonthValues", method = RequestMethod.POST)
    public String postMonthsValues(MasterDataRequest masterDataRequest) {

        Map<String, Double> stringMonthMap = masterDataRequest.getMonthProductionShares();
        Map<Integer, Double> doubleMonthMap = masterDataService.convertMonthsStringToDouble(stringMonthMap);
        dataBean.setMonthlyProductionShares(doubleMonthMap);


        return "redirect:/masterData";
    }



}
