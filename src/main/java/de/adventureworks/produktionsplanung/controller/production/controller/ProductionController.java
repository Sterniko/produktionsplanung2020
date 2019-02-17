package de.adventureworks.produktionsplanung.controller.production.controller;



import de.adventureworks.produktionsplanung.model.DataBean;
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
    private DataBean dataBean;

    public ProductionController() {

    }

    @RequestMapping("/production")
    public String getCustomers(Model model) {

        model.addAttribute("businessDays", dataBean.getSortedBusinessDaysInYear());
        model.addAttribute("businessWeeks", dataBean.getSortedBusinessWeeks());
        model.addAttribute("bikes", dataBean.getBikes());


        return "production";
    }
}
