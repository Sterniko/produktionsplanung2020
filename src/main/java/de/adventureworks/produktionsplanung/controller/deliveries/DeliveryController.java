package de.adventureworks.produktionsplanung.controller.deliveries;

import de.adventureworks.produktionsplanung.controller.util.RequestMapper;
import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import de.adventureworks.produktionsplanung.model.services.DeliveryService;
import de.adventureworks.produktionsplanung.model.services.SortService;
import de.adventureworks.produktionsplanung.model.services.productionTrial.ProductionService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class DeliveryController {

    @Autowired
    DataBean dataBean;

    @Autowired
    SortService sortService;

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    private ProductionService2 productionService2;

    public DeliveryController() {

    }

    @RequestMapping(value= "/deliveries")
    public String getMarketing(Model model, @RequestParam String idSent) {

        LogisticsObject lo = deliveryService.getDeliveryToDeliveryID(idSent);

        List<BusinessDay> businessDayList = new ArrayList<>();
        //get BusinessDays
        businessDayList = sortService.mapToListBusinessDays(dataBean.getBusinessDays());
        //sort them
        businessDayList = sortService.sortBusinessDayList(businessDayList);

        model.addAttribute("idSent",idSent);
        model.addAttribute("lo",lo);
        model.addAttribute("businessWeeks", dataBean.getBusinessWeeks());
        model.addAttribute("businessDays", businessDayList);
        model.addAttribute("deliveryRequest", new DeliveryRequest());

        return "deliveries";
    }

    @RequestMapping(value = "/deliveries", method = RequestMethod.POST)
    public String setDelivery(DeliveryRequest deliveryRequest) {

        BusinessDay businessDay = dataBean.getBusinessDay(deliveryRequest.getDate());
        String deliveryID = deliveryRequest.getEqualsId();
        //TODO : Int in Map auf < 0 prüfen!!!! sonst -werte in Bestellung ....
        Map<Component, Integer> compMap = RequestMapper.mapComponentStringMap(deliveryRequest.getCompMap(), dataBean.getComponents());
        deliveryService.startEvent(deliveryID, businessDay, compMap);

        productionService2.simulateWholeProduction();

        return "redirect:/deliveries?idSent=none";
    }
}
