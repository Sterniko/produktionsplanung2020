package de.adventureworks.produktionsplanung.controller;


import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.services.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class ShipController {

    @Autowired
    private DataBean dataBean;

    @RequestMapping(value="/showShips")
    public String GetShips(Model model){
        model.addAttribute("schiffe", dataBean.getShips());
        return "ship";
    }

    @RequestMapping(value="/deleteShip")
    public String GetShips(@RequestParam String name){
        ShipService.deleteShip(ShipService.getShipByName(name), LocalDate.now());
        return "redirect:ship";
    }

    @RequestMapping(value="/updateShip")
    public String GetShips(@RequestParam String name, LocalDate newArrival){
        ShipService.delayShip(ShipService.getShipByName(name),newArrival);
        return "redirect:ship";
    }

}
