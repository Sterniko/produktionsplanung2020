package de.adventureworks.produktionsplanung.model;

import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Fork;
import de.adventureworks.produktionsplanung.model.entities.bike.Frame;
import de.adventureworks.produktionsplanung.model.entities.bike.Saddle;
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
    private Map<Integer, BusinessWeek> businessWeeks;

    private List<Ship> ships;
    private List<Customer> customers;
    private List<Supplier> suppliers;
    private List<Bike> bikes;
    private List<Frame> frames;
    private List<Fork> forks;
    private List<Saddle> saddles;

    public Data(Map<LocalDate, BusinessDay> businessDays, Map<Integer, BusinessWeek> businessWeeks, List<Ship> ships, List<Customer> customers, List<Supplier> suppliers, List<Bike> bikes, List<Frame> frames, List<Fork> forks, List<Saddle> saddles) {
        this.businessDays = businessDays;
        this.businessWeeks = businessWeeks;
        this.ships = ships;
        this.customers = customers;
        this.suppliers = suppliers;
        this.bikes = bikes;
        this.frames = frames;
        this.forks = forks;
        this.saddles = saddles;
    }

    public Data() {}

    public Map<LocalDate, BusinessDay> getBusinessDays() {
        return businessDays;
    }

    public void setBusinessDays(Map<LocalDate, BusinessDay> businessDays) {
        this.businessDays = businessDays;
    }

    public Map<Integer, BusinessWeek> getBusinessWeeks() {
        return businessWeeks;
    }

    public void setBusinessWeeks(Map<Integer, BusinessWeek> businessWeeks) {
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

    public List<Frame> getFrames() {
        return frames;
    }

    public void setFrames(List<Frame> frames) {
        this.frames = frames;
    }

    public List<Fork> getForks() {
        return forks;
    }

    public void setForks(List<Fork> forks) {
        this.forks = forks;
    }

    public List<Saddle> getSaddles() {
        return saddles;
    }

    public void setSaddles(List<Saddle> saddles) {
        this.saddles = saddles;
    }

}
