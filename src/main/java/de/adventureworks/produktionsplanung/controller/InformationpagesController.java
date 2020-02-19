package de.adventureworks.produktionsplanung.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InformationpagesController {
   // @Autowired
    private String anzeigefeld1;
   // @Autowired
    private String anzeigefeld2;

    public String getAnzeigefeld1() {
        return anzeigefeld1;
    }

    public void setAnzeigefeld1(String anzeigefeld1) {
        this.anzeigefeld1 = anzeigefeld1;
    }

    public String getAnzeigefeld2() {
        return anzeigefeld2;
    }

    public void setAnzeigefeld2(String anzeigefeld2) {
        this.anzeigefeld2 = anzeigefeld2;
    }

    @RequestMapping(value = "/informationpages", method = RequestMethod.GET)
    public String getInfos(){
        return "informationpages";
    }
    public InformationpagesController(){

    }




}
