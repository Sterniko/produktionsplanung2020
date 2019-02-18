package de.adventureworks.produktionsplanung.model.entities.logistics;

import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;

import java.time.LocalDate;
import java.util.*;

public class LogisticsObject {

    private Supplier supplier;
    private int sumAmount;
    private Map<Component, Integer> components;
    private String id;


    private LocalDate arrivalDate;
    private LocalDate departureDate;

    public LogisticsObject() {
    }

    public LogisticsObject(Supplier supplier, int sumAmount, Map<Component, Integer> components) {
        this.supplier = supplier;
        this.sumAmount = sumAmount;
        this.components = components;
    }

    public LogisticsObject(Supplier supplier, int sumAmount, Map<Component, Integer> components, LocalDate arrivalDate, LocalDate departureDate) {
        this.supplier = supplier;
        this.sumAmount = sumAmount;
        this.components = components;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }

    public LogisticsObject(Supplier supplier) {
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public int getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(int sumAmount) {
        this.sumAmount = sumAmount;
    }

    public Map<Component, Integer> getComponents() {
        return components;
    }

    public void setComponents(Map<Component, Integer> components) {
        this.components = components;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public String getId() {
        return toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogisticsObject that = (LogisticsObject) o;
        return sumAmount == that.sumAmount &&
                Objects.equals(supplier, that.supplier) &&
                Objects.equals(components, that.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplier, sumAmount, components);
    }

    private int componentSum() {
        int sum = 0;
        for (Integer i: components.values()) {
           sum+=i;
        }
        return sum;
    }

    public String printForReceivedDelivery() {
        return supplier.getName() + " Sendung: " + arrivalDate + " Menge:" + getSumAmount();
    }

    public String printForSentDelivery() {
        return supplier.getName() + " Ankunft: " + departureDate + " Menge:" + getSumAmount();
    }

    @Override
    public String toString() {

        String componentString = "";

        Queue<Component> keyQueue = new ArrayDeque<>(components.keySet());
        while(!keyQueue.isEmpty()){
            Component component = keyQueue.poll();
            componentString = componentString.concat(component.getName());
            componentString = componentString.concat(components.get(component).toString());
        }

        if (departureDate != null) {
            componentString = departureDate.toString().concat(componentString);
        }
        if (arrivalDate != null) {
            componentString = arrivalDate.toString().concat(componentString);
        }

        return (componentString);
    }
}
