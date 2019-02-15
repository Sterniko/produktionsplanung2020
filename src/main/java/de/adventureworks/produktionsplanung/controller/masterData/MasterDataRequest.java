package de.adventureworks.produktionsplanung.controller.masterData;

public class MasterDataRequest {

    private int hourlyCapacity;

    private int yearlyCapacity;

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
}
