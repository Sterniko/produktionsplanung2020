package de.adventureworks.produktionsplanung.controller.deliveries;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adventureworks.produktionsplanung.controller.util.RequestMapper;
import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import de.adventureworks.produktionsplanung.model.services.DeliveryService;
import de.adventureworks.produktionsplanung.model.services.SortService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.rmi.runtime.Log;

import java.beans.Expression;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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

        model.addAttribute("deliveryRequest", new DeliveryRequest());


        return "deliveries";
    }

    @RequestMapping(value = "/deliveries", method = RequestMethod.POST)
    public String setDelivery(DeliveryRequest deliveryRequest) {


        HashMap<Component,Integer> helperMap;
        HashMap<BusinessDay,LogisticsObject> bdLoMap;


        //Get Post Data
        //Post Map Component,Integer -> Neue Bestellung
        //Post deliveryID -> Für diese Bestellung

        int deliveryID = deliveryRequest.getId();
        Map<Component, Integer> compMap = RequestMapper.mapComponentStringMap(deliveryRequest.getCompMap(), dataBean.getComponents());
        int sumAmount = 0;
        List<BusinessDay> businessDays = deliveryService.getBusinessDayToDeliveryID(deliveryID);

        businessDays = sortService.sortBusinessDayList(businessDays);


        if((businessDays.size() > 1)){
            BusinessDay departureDay = businessDays.get(0);
            BusinessDay arrivalDay = businessDays.get(1);
            List<LogisticsObject> sentDeliveries = departureDay.getSentDeliveries();
            List<LogisticsObject> receivedDeliveries = arrivalDay.getReceivedDeliveries();

            deliveryService.setNewDelivery(sentDeliveries,deliveryID,compMap);
            deliveryService.setNewDelivery(receivedDeliveries,deliveryID,compMap);

        }











     //   arrivalDay.setReceivedDeliveries(newListArrival);
       // departureDay.setSentDeliveries(newListDeparture);


        //Lo.arrival.setReceivedDel -> iteriere durch alle received bis eine gefunden die so ist wie diese! überschreibe mit New Map Comp
        //Lo.departure.setSendReceived -> iteriere durch alle send bis eine gefunden die so ist wie diese! überschreibe mit New Map Comp

        return "redirect:/deliveries";

    }
}
