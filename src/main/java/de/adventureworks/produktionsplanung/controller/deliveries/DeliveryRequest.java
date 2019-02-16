package de.adventureworks.produktionsplanung.controller.deliveries;

import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import jdk.nashorn.internal.parser.JSONParser;
import net.minidev.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Map;

public class DeliveryRequest {





    private Map<String, Integer> compMap;
    private int id;



    public DeliveryRequest() {
    }

    public DeliveryRequest(Map<String, Integer> compMap, int id) {
        this.compMap = compMap;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Integer> getCompMap() {
        return compMap;
    }

    public void setCompMap(Map<String, Integer> compMap) {
        this.compMap = compMap;
    }






    @Override
    public String toString() {
        return "MarketingRequest{" +
                ", compMap=" + compMap +
                '}';
    }

}