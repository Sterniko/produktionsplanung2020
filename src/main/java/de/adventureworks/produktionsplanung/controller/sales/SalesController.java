package de.adventureworks.produktionsplanung.controller.sales;


import de.adventureworks.produktionsplanung.controller.util.RequestMapper;
import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.Map;

@Controller
public class SalesController {

    @Autowired
    private DataBean dataBean;

    public SalesController() {

    }

    @RequestMapping("/sales")
    public String getProduction(Model model) {
        model.addAttribute("businessWeeks", dataBean.getBusinessWeeks());
        model.addAttribute("businessDays", dataBean.getBusinessDays());
        model.addAttribute("bikes", dataBean.getBikes());
        model.addAttribute("countries", Country.class.getEnumConstants()); //all declared countries
        model.addAttribute("salesRequest", new SalesRequest());
        return "sales";
    }

    @RequestMapping(value = "/sales", method = RequestMethod.POST)
    public String postAdditionalProduction(SalesRequest salesRequest) {

        System.out.println(salesRequest);
        Country country = salesRequest.getCountry();
        LocalDate customerDeliveryDate = salesRequest.getCustomerDeliveryDate();
        LocalDate sendingDate = null; //TODO: Methode für spätestes sendingdate
        Map<Bike, Integer> bikeMap = RequestMapper.mapBikeStringMap(salesRequest.getBikeMap(), dataBean.getBikes());
        boolean isPrio = RequestMapper.mapStringToBoolean(salesRequest.getPrio());
        BusinessDay deliveryDay = dataBean.getBusinessDay(customerDeliveryDate);
        return "redirect:/sales";
    }

}
