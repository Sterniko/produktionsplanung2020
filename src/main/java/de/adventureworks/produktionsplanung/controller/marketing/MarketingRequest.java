package de.adventureworks.produktionsplanung.controller.marketing;

import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Map;

public class MarketingRequest {

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate placementDate;

    private int businessWeek;

    private Map<String, Integer> bikeMap;


    public LocalDate getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(LocalDate placementDate) {
        this.placementDate = placementDate;
    }

    public int getBusinessWeek() {
        return this.businessWeek;
    }

    public void setBusinessWeek(int businessWeek) {
        this.businessWeek = businessWeek;
    }

    public Map<String, Integer> getBikeMap() {
        return bikeMap;
    }

    public void setBikeMap(Map<String, Integer> bikeMap) {
        this.bikeMap = bikeMap;
    }


    public MarketingRequest() {
    }

    public MarketingRequest(LocalDate placementDate, int businessWeek, Map<String, Integer> bikeMap) {
        this.placementDate = placementDate;
        this.businessWeek = businessWeek;
        this.bikeMap = bikeMap;

    }



    @Override
    public String toString() {
        return "MarketingRequest{" +
                "placementDate=" + placementDate +
                ",businessWeek=" + businessWeek +
                ", bikeMap=" + bikeMap +
                '}';
    }

}
