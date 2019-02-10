package de.adventureworks.produktionsplanung.model.entities.businessPeriods;

import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BusinessDay implements Comparable<BusinessDay> {

    private LocalDate date;


    private Map<Supplier, LogisticsObject> pendingSupplierAmount;
    private List<LogisticsObject> sentDeliveries;
    private List<LogisticsObject> receivedDeliveries;

    private Map<Country, Boolean> workingDays;

    private Map<Bike, Integer> plannedProduction;
    private Map<Bike, Integer> additionalProduction;
    private Map<Bike, Integer> actualProduction;

    private Map<Component, Integer> warehouseStock;

    public BusinessDay(LocalDate date, Map<Supplier, LogisticsObject> pendingSupplierAmount, List<LogisticsObject> sentDeliveries, List<LogisticsObject> receivedDeliveries, Map<Country, Boolean> workingDays, Map<Bike, Integer> plannedProduction, Map<Bike, Integer> additionalProduction, Map<Bike, Integer> actualProduction, Map<Component, Integer> warehouseStock) {
        this.date = date;
        this.pendingSupplierAmount = pendingSupplierAmount;
        this.sentDeliveries = sentDeliveries;
        this.receivedDeliveries = receivedDeliveries;
        this.workingDays = workingDays;
        this.plannedProduction = plannedProduction;
        this.additionalProduction = additionalProduction;
        this.actualProduction = actualProduction;
        this.warehouseStock = warehouseStock;
    }

    public BusinessDay(){}

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public Map<Supplier, LogisticsObject> getPendingSupplierAmount() {
        return pendingSupplierAmount;
    }

    public void setPendingSupplierAmount(Map<Supplier, LogisticsObject> pendingSupplierAmount) {
        this.pendingSupplierAmount = pendingSupplierAmount;
    }

    public List<LogisticsObject> getSentDeliveries() {
        return sentDeliveries;
    }

    public void setSentDeliveries(List<LogisticsObject> sentDeliveries) {
        this.sentDeliveries = sentDeliveries;
    }

    public List<LogisticsObject> getReceivedDeliveries() {
        return receivedDeliveries;
    }

    public void setReceivedDeliveries(List<LogisticsObject> receivedDeliveries) {
        this.receivedDeliveries = receivedDeliveries;
    }

    public Map<Country, Boolean> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Map<Country, Boolean> workingDays) {
        this.workingDays = workingDays;
    }

    public Map<Bike, Integer> getPlannedProduction() {
        return plannedProduction;
    }

    public void setPlannedProduction(Map<Bike, Integer> plannedProduction) {
        this.plannedProduction = plannedProduction;
    }

    public Map<Bike, Integer> getAdditionalProduction() {
        return additionalProduction;
    }

    public void setAdditionalProduction(Map<Bike, Integer> additionalProduction) {
        this.additionalProduction = additionalProduction;
    }

    public Map<Bike, Integer> getActualProduction() {
        return actualProduction;
    }

    public void setActualProduction(Map<Bike, Integer> actualProduction) {
        this.actualProduction = actualProduction;
    }

    public Map<Component, Integer> getWarehouseStock() {
        return warehouseStock;
    }

    public void setWarehouseStock(Map<Component, Integer> warehouseStok) {
        this.warehouseStock = warehouseStok;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessDay that = (BusinessDay) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(pendingSupplierAmount, that.pendingSupplierAmount) &&
                Objects.equals(sentDeliveries, that.sentDeliveries) &&
                Objects.equals(receivedDeliveries, that.receivedDeliveries) &&
                Objects.equals(workingDays, that.workingDays) &&
                Objects.equals(plannedProduction, that.plannedProduction) &&
                Objects.equals(additionalProduction, that.additionalProduction) &&
                Objects.equals(actualProduction, that.actualProduction) &&
                Objects.equals(warehouseStock, that.warehouseStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, pendingSupplierAmount, sentDeliveries, receivedDeliveries, workingDays, plannedProduction, additionalProduction, actualProduction, warehouseStock);
    }

    @Override
    public String toString() {
        return "BusinessDay{" +
                "date=" + date +
                ", pendingSupplierAmount=" + pendingSupplierAmount +
                ", sentDeliveries=" + sentDeliveries +
                ", receivedDeliveries=" + receivedDeliveries +
                ", workingDays=" + workingDays +
                ", plannedProduction=" + plannedProduction +
                ", additionalProduction=" + additionalProduction +
                ", actualProduction=" + actualProduction +
                ", warehouseStock=" + warehouseStock +
                '}';
    }

    @Override
    public int compareTo(BusinessDay o) {
        return this.date.compareTo(o.getDate());
    }
}
