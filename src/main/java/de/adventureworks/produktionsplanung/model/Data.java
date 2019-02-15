package de.adventureworks.produktionsplanung.model;

import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import de.adventureworks.produktionsplanung.model.entities.external.Customer;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Data {

    private Map<LocalDate, BusinessDay> businessDays;
    private List<BusinessWeek> businessWeeks;


    private List<Ship> ships;
    private List<Customer> customers;
    private List<Supplier> suppliers;
    private List<Bike> bikes;
    private List<Component> components;
    private List<Integer> shifts;
    private int hourlyCapacity;

    public Data(Map<LocalDate, BusinessDay> businessDays, List<BusinessWeek> businessWeeks, List<Ship> ships, List<Customer> customers, List<Supplier> suppliers, List<Bike> bikes, List<Component> components, List<Integer> shifts, int hourlyCapacity) {
        this.businessDays = businessDays;
        this.businessWeeks = businessWeeks;
        this.ships = ships;
        this.customers = customers;
        this.suppliers = suppliers;
        this.bikes = bikes;
        this.components = components;
        this.shifts = shifts;
        this.hourlyCapacity = hourlyCapacity;
    }

    public Data() {}

    public Map<LocalDate, BusinessDay> getBusinessDays() {
        return businessDays;
    }

    public void setBusinessDays(Map<LocalDate, BusinessDay> businessDays) {
        this.businessDays = businessDays;
    }

    public List<BusinessWeek> getBusinessWeeks() {
        return businessWeeks;
    }

    public void setBusinessWeeks(List<BusinessWeek> businessWeeks) {
        this.businessWeeks = businessWeeks;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public List<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(List<Bike> bikes) {
        this.bikes = bikes;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<?> components) {
        this.components = (List<Component>)components;
    }

    public void updateComponent(List<?> comps){
       for(int i = 0; i < comps.size();i++){
           components.add((Component)comps.get(i));
       }
    }

    public List<Integer> getShifts() {
        return shifts;
    }

    public void setShifts(List<Integer> shifts) {
        this.shifts = shifts;
    }

    public int getHourlyCapacity() {
        return hourlyCapacity;
    }

    public void setHourlyCapacity(int hourlyCapacity) {
        this.hourlyCapacity = hourlyCapacity;
    }
}
