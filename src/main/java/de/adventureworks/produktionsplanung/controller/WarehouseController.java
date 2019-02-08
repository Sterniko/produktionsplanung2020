package de.adventureworks.produktionsplanung.controller;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Map;

@Controller
public class WarehouseController {

    private DataBean dataBean;

    public WarehouseController(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    @RequestMapping("/warehouse")
    public String getBusinessWeeks(Model model) {
        model.addAttribute("businessWeeks", dataBean.getBusinessWeeks());
        model.addAttribute("components", dataBean.getComponents());
        return "warehouse";
    }

/*
    public void postComponentChange(LocalDate date, Map<Component, Integer> newStock) {
        BusinessDay businessDay =;
        //TODO: businessDay of LocalDate

        businessDay.setWarehouseStok(newStock);



    }
    */

}
