package de.adventureworks.produktionsplanung.controller.deliveries;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Map;

public class DeliveryRequest {


    private Map<String, Integer> compMap;

    private String equalsid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public DeliveryRequest() {
    }

    public DeliveryRequest(Map<String, Integer> compMap, String id, LocalDate date) {
        this.compMap = compMap;
        this.equalsid = id;
        this.date = date;
    }

    public String getEqualsId() {
        return equalsid;
    }

    public void setEqualsId(String id) {
        this.equalsid = id;
    }

    public Map<String, Integer> getCompMap() {
        return compMap;
    }

    public void setCompMap(Map<String, Integer> compMap) {
        this.compMap = compMap;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MarketingRequest{" +
                ", compMap=" + compMap +
                '}';
    }

}
