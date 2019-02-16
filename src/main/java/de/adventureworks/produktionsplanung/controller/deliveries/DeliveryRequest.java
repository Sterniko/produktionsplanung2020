package de.adventureworks.produktionsplanung.controller.deliveries;

import java.util.Map;

public class DeliveryRequest {


    private Map<String, Integer> compMap;
    private String id;

    private String testField = "AFA";


    public DeliveryRequest() {
    }

    public DeliveryRequest(Map<String, Integer> compMap, String id) {
        this.compMap = compMap;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestField() {
        return testField;
    }

    public void setTestField(String testField) {
        this.testField = testField;
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
