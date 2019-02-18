package de.adventureworks.produktionsplanung.controller.ship;


import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.events.IEvent;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.services.ShipService;
import de.adventureworks.produktionsplanung.model.services.init.DataInitService;
import de.adventureworks.produktionsplanung.model.services.productionTrial.ProductionService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShipController {

    @Autowired
    private DataBean dataBean;
    @Autowired
    private ShipService service;
    @Autowired
    ProductionService2 productionService2;
    @Autowired
    private DataInitService dataInitService;


    @RequestMapping(value = "/showShips")
    public String GetShips(Model model) {
        model.addAttribute("schiffe", dataBean.getShips());
        model.addAttribute("deleteShipRequest", new DeleteShipRequest());
        model.addAttribute("updateShipRequest", new UpdateShipRequest());
        return "ship";
    }

    @RequestMapping(value = "/deleteShip", method = RequestMethod.POST)
    public String GetShips(DeleteShipRequest deleteShipRequest) {
        String shipName = deleteShipRequest.getName();
        LocalDate deleteDay = deleteShipRequest.getDeleteDate();
        Ship ship = service.getShipByName(shipName);
        service.startEvent(ship, deleteDay, dataBean);
        productionService2.simulateWholeProduction();
        return "redirect:/showShips";
    }

    @RequestMapping(value = "/updateShip", method = RequestMethod.POST)
    public String GetShips(UpdateShipRequest updateShipRequest) {
        String shipName = updateShipRequest.getName();
        Ship ship = service.getShipByName(shipName);
        LocalDate newArrival = updateShipRequest.getNewArrival();
        service.delayShip(ship, newArrival);
        productionService2.simulateWholeProduction();
        return "redirect:/showShips";
    }

    //TODO erstmal habe ich hierrein getan damits niemnad sonst stört muss aber theoretish noch ein eigenen COntroller
    //TODO bekommen oder andere Lösung---Sercan
    @RequestMapping(value = "/")
    public String goHome(Model model) {
        return "home";
    }

    @RequestMapping(value = "/bikechart")
    public String charts(Model model) {

        LocalDate date = LocalDate.of(2019,1,1);
        int i = 1;
        int cumPlanAmount = 0;
        int cumActualAmount = 0;
        int additAmount = 0;
        while (date.isBefore(LocalDate.of(2019,12,31))) {
            cumPlanAmount+= dataBean.getBusinessDay(date).getSumOfPlannedDailyProduction();
            String name = "p" + i;
            model.addAttribute(name, cumPlanAmount);

            cumActualAmount += dataBean.getBusinessDay(date).getSumOfActualDailyProduction();
            String cname = "c" + i;
            model.addAttribute(cname, cumActualAmount);

            cumActualAmount += dataBean.getBusinessDay(date).getSumOfAdditionalProduction();
            String aname = "a" + i;
            model.addAttribute(cname, cumActualAmount);

            String oname = "o" + i;
            model.addAttribute(oname, dataBean.getBusinessDay(date).getProductionOverhangSum());


            i++;

            date = date.plusDays(1);
        }


        return "bikechart";
    }


    @RequestMapping(value = "/refresh")
    public String refresh(Model model) {

        List<BusinessDay> bdList = new ArrayList<>(dataBean.getBusinessDays().values());
        for (BusinessDay bd : bdList) {
            List<IEvent> eventList = new ArrayList<>();
            bd.setEventList(eventList);
        }

        //dataInitService.init();
        productionService2.simulateWholeProduction();

        return "redirect:/";
    }

}
