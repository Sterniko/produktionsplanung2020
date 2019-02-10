package de.adventureworks.produktionsplanung.model.entities.external;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.logistics.Delivery;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ship implements Comparable<Ship>{

    private String name;
    private LocalDate departure;
    private LocalDate arrival;
    private List<Delivery> deliveries;

    @Autowired
    DataBean databean;


    public Ship(String name, LocalDate departure, LocalDate arrival) {
        this.name = name;
        this.departure = departure;
        this.arrival = arrival;
        deliveries = new ArrayList();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    public LocalDate getArrival() {
        return arrival;
    }

    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return Objects.equals(name, ship.name) &&
                Objects.equals(departure, ship.departure) &&
                Objects.equals(arrival, ship.arrival);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, departure, arrival);
    }


    @Override
    public int compareTo(Ship o) {
        return this.getDeparture().compareTo(o.getDeparture());
    }

    @Override
    public String toString() {
        return "Ship{" +
                "name='" + name + '\'' +
                ", departure=" + departure +
                ", arrival=" + arrival +
                '}';
    }

    public void addOrder(Delivery order){
        deliveries.add(order);
    }

    public void deleteOrder(Delivery order){
        deliveries.remove(order);
    }

    public void addOrders(List<Delivery> newOrders){
        for(Delivery e : newOrders){
            addOrder(e);
        }
    }

    public void deleteOrders(List<Delivery> toDelete){
        for(Delivery e: toDelete){
            deleteOrder(e);
        }
    }

    public void delayArrival(LocalDate newArrival) {
        arrival = newArrival;
        for (Delivery e : deliveries) {
            LocalDate oldDate = e.getArrival();
            BusinessDay oldBDay = databean.getBusinessDay(oldDate);
            oldBDay.getReceivedDeliveries().remove(e);
            databean.getBusinessDay(newArrival).getReceivedDeliveries().add(e);
            e.setArrival(newArrival);
        }

    }

}
