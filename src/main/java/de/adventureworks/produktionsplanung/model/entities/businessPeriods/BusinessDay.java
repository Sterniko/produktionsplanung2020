package de.adventureworks.produktionsplanung.model.entities.businessPeriods;

import de.adventureworks.produktionsplanung.model.entities.bike.*;
import de.adventureworks.produktionsplanung.model.entities.events.IEvent;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;

import java.time.LocalDate;
import java.util.*;

public class BusinessDay implements Comparable<BusinessDay> {

    private LocalDate date;
    private BusinessWeek businessWeek;

    private Map<Supplier, LogisticsObject> pendingSupplierAmount;
    private List<LogisticsObject> sentDeliveries;
    private List<LogisticsObject> receivedDeliveries;

    private Map<Country, Boolean> workingDays;
    private Map<Bike, Integer> plannedProduction;
    private Map<Bike, Integer> productionOverhang;
    private Map<Bike, Integer> additionalProduction;
    private Map<Bike, Integer> prioProduction;
    private Map<Bike, Integer> actualProduction;

    private Map<Component, Integer> warehouseStock;
    private Map<Component, Integer> beforeProductionWarehouseStock;

    private List<IEvent> eventList;

    public BusinessDay(){
        productionOverhang = new HashMap<>();
    }

    public BusinessDay(LocalDate date, Map<Supplier, LogisticsObject> pendingSupplierAmount, List<LogisticsObject> sentDeliveries, List<LogisticsObject> receivedDeliveries, Map<Country, Boolean> workingDays, Map<Bike, Integer> plannedProduction, Map<Bike, Integer> additionalProduction, Map<Bike, Integer> actualProduction, Map<Component, Integer> warehouseStock) {
        productionOverhang = new HashMap<>();


        this.date = date;
        this.pendingSupplierAmount = pendingSupplierAmount;
        this.sentDeliveries = sentDeliveries;
        this.receivedDeliveries = receivedDeliveries;
        this.workingDays = workingDays;
        this.plannedProduction = plannedProduction;
        this.additionalProduction = additionalProduction;
        this.actualProduction = actualProduction;
        this.warehouseStock = warehouseStock;
        eventList = new ArrayList<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public String germanDate(){

        String returnvalue =String.format("%02d.%02d.%d",date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        return returnvalue;

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

    public Integer getFrameStockNumber() {
        Integer number = 0;
        for (Component c: warehouseStock.keySet()) {
            if(c.getClass() == Frame.class) {
                number += warehouseStock.get(c);
            }
        }
        return number;

    }

    public Integer getSaddleStockNumber() {
        Integer number = 0;
        for (Component c: warehouseStock.keySet()) {
            if(c.getClass() == Saddle.class) {
                number += warehouseStock.get(c);
            }
        }
        return number;


    }

    public Integer getForkStockNumber() {
        Integer number = 0;
        for (Component c: warehouseStock.keySet()) {
            if(c.getClass() == Fork.class) {
                number += warehouseStock.get(c);
            }
        }
        return number;


    }

    public void setWarehouseStock(Map<Component, Integer> warehouseStock) {
        this.warehouseStock = warehouseStock;
    }

    public Map<Component, Integer> getBeforeProductionWarehouseStock() {
        return beforeProductionWarehouseStock;
    }

    public void setBeforeProductionWarehouseStock(Map<Component, Integer> beforeProductionWarehouseStock) {
        this.beforeProductionWarehouseStock = beforeProductionWarehouseStock;
    }

    public BusinessWeek getBusinessWeek() {
        return businessWeek;
    }

    public void setBusinessWeek(BusinessWeek businessWeek) {
        this.businessWeek = businessWeek;
    }

    public Map<Bike, Integer> getProductionOverhang() {
        return productionOverhang;
    }

    public void setProductionOverhang(Map<Bike, Integer> productionOverhang) {
        this.productionOverhang = productionOverhang;
    }

    public Map<Bike, Integer> getPrioProduction() {
        return prioProduction;
    }

    public void setPrioProduction(Map<Bike, Integer> prioProduction) {
        this.prioProduction = prioProduction;
    }

    public List<IEvent> getEventList() {
        return eventList;
    }

    public Integer getSumOfPlannedDailyProduction() {
        Integer sum = 0;
        for (Bike bike: plannedProduction.keySet()) {
            sum += plannedProduction.get(bike);
        }
        return sum;
    }

    public Integer getSumOfActualDailyProduction() {
        Integer sum = 0;
        for (Integer i: actualProduction.values()) {
            sum += i;
        }
        return sum;
    }

    public Integer getSumOfAdditionalProduction() {
        Integer sum = 0;
        for (Bike bike: additionalProduction.keySet()) {
            sum += additionalProduction.get(bike);
        }
        return sum;
    }

    public Map<Bike, Integer> getPlannedAndAdditionalMinusActualProduction() {
        HashMap<Bike, Integer> resultMap = new HashMap<>();
        for (Bike bike: plannedProduction.keySet()) {
            if (actualProduction.containsKey(bike)) {
                resultMap.put(bike, plannedProduction.get(bike) + additionalProduction.get(bike) - actualProduction.get(bike) );
            }
        }
        return resultMap;
    }

    public Integer getProductionOverhangSum() {
        Integer sum = 0;
        for (Integer i: productionOverhang.values()) {
            sum+= i;
        }
        return sum;
    }



    public void setEventList(List<IEvent> eventList) {
        this.eventList = eventList;
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
