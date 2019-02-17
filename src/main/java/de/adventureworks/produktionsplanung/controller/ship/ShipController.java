package de.adventureworks.produktionsplanung.controller.ship;


import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.services.ShipService;
import de.adventureworks.produktionsplanung.model.services.productionTrial.ProductionService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;

@Controller
public class ShipController {

    @Autowired
    private DataBean dataBean;
    @Autowired
    private ShipService service;
    @Autowired
    ProductionService2 productionService2;


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

}
