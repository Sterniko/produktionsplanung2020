package de.adventureworks.produktionsplanung.controller.ship;


import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.services.ArrivalCalculator;
import de.adventureworks.produktionsplanung.model.services.ShipService;
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


    @RequestMapping(value="/showShips")
    public String GetShips(Model model){
        model.addAttribute("schiffe", dataBean.getShips());
        model.addAttribute("deleteShipRequest", new DeleteShipRequest());
        model.addAttribute("updateShipRequest", new UpdateShipRequest());
        return "ship";
    }

    @RequestMapping(value="/deleteShip", method = RequestMethod.POST)
    public String GetShips(DeleteShipRequest deleteShipRequest){
        String shipName = deleteShipRequest.getName();
        //Ship ship = RequestMapper.mapShip(shipName, dataBean.getShips());
        //TODO Service hier als Klasse verwendet -.- ---Sercan
        Ship ship =  service.getShipByName(shipName);
        service.deleteShip(ship,LocalDate.now(),dataBean);
        return "redirect:/showShips";
    }

    @RequestMapping(value="/updateShip", method = RequestMethod.POST)
    public String GetShips(UpdateShipRequest updateShipRequest){
        String shipName = updateShipRequest.getName();
        Ship ship= service.getShipByName(shipName);
        //Ship ship = RequestMapper.mapShip(shipName, dataBean.getShips());
        LocalDate newArrival = updateShipRequest.getNewArrival();
        //VIEL SPASS BEIM WEITERMACHEN :)
        service.delayShip(ship,newArrival);
        return "redirect:/showShips";
    }

    //TODO erstmal habe ich hierrein getan damits niemnad sonst stört muss aber theoretish noch ein eigenen COntroller
    //TODO bekommen oder andere Lösung---Sercan
    @RequestMapping(value="/")
    public String goHome(Model model){
        return "home";
    }

    @RequestMapping(value="/aaaa")
    public String CalcTest(Model model){
        System.out.println();
        System.out.println(ArrivalCalculator.calculate(LocalDate.of(2019,1,1), 34, Country.CHINA,dataBean));
        System.out.println();
        return "home";
    }


}
