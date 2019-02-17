package de.adventureworks.produktionsplanung.model;

import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import de.adventureworks.produktionsplanung.model.entities.external.Customer;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.services.init.DataInitService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
        if (data.getBusinessDays().containsKey(date)) {
            return data.getBusinessDays().get(date);
        } else {
            return null;
        }
    }

    public List<BusinessDay> getSortedBusinessDaysInYear(){
        List<LocalDate> keyDates = new ArrayList<>(getBusinessDays().keySet());
        List<BusinessDay> resultList = new ArrayList<>();
        Collections.sort(keyDates);
        for (LocalDate date: keyDates) {
            if (date.getYear() == 2019) {
                resultList.add(data.getBusinessDays().get(date));

            }
        }
        return resultList;
    }

    public List<BusinessWeek> getSortedBusinessWeeks() {
        List<BusinessWeek> resultList = data.getBusinessWeeks();
        Collections.sort(resultList);
        return resultList;
    }

    public List<BusinessDay> getBusinnesDayListFromDate(LocalDate startDate){
        List<LocalDate> keyDates = new ArrayList<>(getBusinessDays().keySet());
        List<BusinessDay> resultList = new ArrayList<>();
        Collections.sort(keyDates);

        for (LocalDate date: keyDates) {
            if(date.isAfter(startDate)){
                resultList.add(data.getBusinessDays().get(date));
            }
        }

        return resultList;
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

    public List<Integer> getShifts() {
        return data.getShifts();
    }

    public void setShifts(List<Integer> shifts) {
        data.setShifts(shifts);
    }

    public int getHourlyCapacity() {
        return data.getHourlyCapacity();
    }

    public void setHourlyCapacity(int hourlyCapacity) {
        data.setHourlyCapacity(hourlyCapacity);
    }

    public int getYearlyProduction() {
        return data.getYearlyProduction();
    }

    public void setYearlyProduction(int yearlyProduction) {
        data.setYearlyProduction(yearlyProduction);
    }

    public int getActualYearlyProduction() {
        int sum = 0;
        for (LocalDate date: data.getBusinessDays().keySet() ) {
            BusinessDay businessDay = getBusinessDay(date);
            for (Bike bike: businessDay.getActualProduction().keySet()) {
                sum += businessDay.getActualProduction().get(bike);
            }

        }
        return sum;
    }

    public Map<Bike, Double> getBikeProductionShares() {
        return data.getBikeProductionShares();
    }

    public void setBikeProductionShares(Map<Bike, Double> bikeProductionShares) {
        data.setBikeProductionShares(bikeProductionShares);
    }

    public Map<Integer, Double> getMonthlyProductionShares() {
        return data.getMonthlyProductionShares();
    }

    public void setMonthlyProductionShares(Map<Integer, Double> monthlyProductionShares) {
        data.setMonthlyProductionShares(monthlyProductionShares);
    }
}
