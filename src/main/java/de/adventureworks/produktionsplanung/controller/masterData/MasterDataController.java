package de.adventureworks.produktionsplanung.controller.masterData;

import de.adventureworks.produktionsplanung.model.DataBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "masterData";
    }


}
