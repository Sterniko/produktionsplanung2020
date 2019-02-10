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
import de.adventureworks.produktionsplanung.model.services.DataInitService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class DataBean {

    private Data data;
    private DataInitService dataInitService;

    public DataBean(DataInitService dataInitService) {
        this.dataInitService = dataInitService;
        this.data = dataInitService.getData();
    }






    //Methode neu, brauche ich Sercan
    public BusinessDay getBusinessDay(LocalDate date){
        return data.getBusinessDays().get(date);
    }

    public Map<LocalDate, BusinessDay> getBusinessDays() {
        return data.getBusinessDays();
    }

    public void setBusinessDays(Map<LocalDate, BusinessDay> businessDays) {
        data.setBusinessDays(businessDays);
    }

    public Map<Integer, BusinessWeek> getBusinessWeeks() {
        return data.getBusinessWeeks();
    }

    public void setBusinessWeeks(Map<Integer, BusinessWeek> businessWeeks) {
        data.setBusinessWeeks(businessWeeks);
    }

    public List<Ship> getShips() {
        return data.getShips();
    }

    public void setShips(List<Ship> ships) {
        data.setShips(ships);
    }

    public List<Customer> getCustomers() {
        return data.getCustomers();
    }

    public void setCustomers(List<Customer> customers) {
        data.setCustomers(customers);
    }

    public List<Supplier> getSuppliers() {
        return data.getSuppliers();
    }

    public void setSuppliers(List<Supplier> suppliers) {
        data.setSuppliers(suppliers);
    }

    public List<Bike> getBikes() {
        return data.getBikes();
    }

    public void setBikes(List<Bike> bikes) {
        data.setBikes(bikes);
    }

    public List<Frame> getFrames() {
        return data.getFrames();
    }

    public void setFrames(List<Frame> frames) {
        data.setFrames(frames);
    }

    public List<Fork> getForks() {
        return data.getForks();
    }

    public void setForks(List<Fork> forks) {
        data.setForks(forks);
    }

    public List<Saddle> getSaddles() {
        return data.getSaddles();
    }

    public void setSaddles(List<Saddle> saddles) {
        data.setSaddles(saddles);
    }


}
