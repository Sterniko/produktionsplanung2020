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
        System.out.println("data initialized");
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

    public List<BusinessWeek> getBusinessWeeks() {
        return data.getBusinessWeeks();
    }

    public void setBusinessWeeks(List<BusinessWeek> businessWeeks) {
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

    public void setComponents(List<de.adventureworks.produktionsplanung.model.entities.bike.Component> components){
        data.setComponents(components);
    }
    public List<de.adventureworks.produktionsplanung.model.entities.bike.Component> getComponents(){
        return data.getComponents();
    }


}
