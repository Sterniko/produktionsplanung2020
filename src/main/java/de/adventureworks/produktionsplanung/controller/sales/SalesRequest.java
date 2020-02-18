package de.adventureworks.produktionsplanung.controller.sales;

import de.adventureworks.produktionsplanung.model.entities.external.Country;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Map;

public class SalesRequest {

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate placementDate;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate customerDeliveryDate;
    private Country country;
    private Map<String, Integer> bikeMap;
    String prio;


    public SalesRequest() {
    }

    public SalesRequest(LocalDate placementDate, LocalDate customerDeliveryDate, Country country, Map<String, Integer> bikeMap, String prio) {
        this.placementDate = placementDate;
        this.customerDeliveryDate = customerDeliveryDate;
        this.country = country;
        this.bikeMap = bikeMap;
        this.prio = prio;
    }

    public LocalDate getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(LocalDate placementDate) {
        this.placementDate = placementDate;
    }

    public LocalDate getCustomerDeliveryDate() {
        return customerDeliveryDate;
    }

    public void setCustomerDeliveryDate(LocalDate customerDeliveryDate) {
        this.customerDeliveryDate = customerDeliveryDate;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Map<String, Integer> getBikeMap() {
        return bikeMap;
    }

    public void setBikeMap(Map<String, Integer> bikeMap) {
        this.bikeMap = bikeMap;
    }

    public String getPrio() {
        return prio;
    }

    public void setPrio(String prio) {
        this.prio = prio;
    }

    @Override
    public String toString() {
        return "SalesRequest{" +
                "placementDate=" + placementDate +
                ", customerDeliveryDate=" + customerDeliveryDate +
                ", country=" + country +
                ", bikeMap=" + bikeMap +
                ", prio=" + prio +
                '}';
    }

}
