package de.adventureworks.produktionsplanung.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShipController {

    @RequestMapping(value="/showShips")
    public String GetShips(Model model){
       return "ships";
    }


}
