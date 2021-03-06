package de.adventureworks.produktionsplanung.controller.masterData;

import java.util.Map;

public class MasterDataRequest {

    private int hourlyCapacity;

    private int yearlyCapacity;

    private Map<String, Double> bikeProductionShares;

    private Map<String, Double> monthProductionShares;



    public MasterDataRequest() {
    }



    public int getHourlyCapacity() {
        return hourlyCapacity;
    }

    public void setHourlyCapacity(int hourlyCapacity) {
        this.hourlyCapacity = hourlyCapacity;
    }

    public int getYearlyCapacity() {
        return yearlyCapacity;
    }

    public void setYearlyCapacity(int yearlyCapacity) {
        this.yearlyCapacity = yearlyCapacity;
    }

    public Map<String, Double> getBikeProductionShares() {
        return bikeProductionShares;
    }

    public void setBikeProductionShares(Map<String, Double> bikeProductionShares) {
        this.bikeProductionShares = bikeProductionShares;
    }

    public Map<String, Double> getMonthProductionShares() {
        return monthProductionShares;
    }

    public void setMonthProductionShares(Map<String, Double> monthProductionShares) {
        this.monthProductionShares = monthProductionShares;
    }
}
