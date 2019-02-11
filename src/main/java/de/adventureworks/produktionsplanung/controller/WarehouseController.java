package de.adventureworks.produktionsplanung.controller;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        model.addAttribute("businessDays", dataBean.getBusinessDays());
        model.addAttribute("components", dataBean.getComponents());
        return "warehouse";
    }

    @RequestMapping(value = "/warehouse", method = RequestMethod.POST)
    public void updateComponentStock(LocalDate date, Map<Component, Integer> newStock) {

    }

}