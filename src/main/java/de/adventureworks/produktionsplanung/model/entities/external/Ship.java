package de.adventureworks.produktionsplanung.model.entities.external;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ship implements Comparable<Ship> {

    private String name;
    private LocalDate departure;
    private LocalDate arrival;
    private List<LogisticsObject> deliveries;

    @Autowired
    DataBean databean;

    public Ship() {
    }

    public Ship(String name, LocalDate departure, LocalDate arrival) {
        this.name = name;
        this.departure = departure;
        this.arrival = arrival;
        this.deliveries = new ArrayList();

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

    public String getGermanDeparture() {
        String returnvalue = String.format("%02d.%02d.%d", departure.getDayOfMonth(), departure.getMonthValue(), departure.getYear());
        return returnvalue;
    }

    public String getGermanArrival() {
        String returnvalue = String.format("%02d.%02d.%d", arrival.getDayOfMonth(), arrival.getMonthValue(), arrival.getYear());
        return returnvalue;
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


    public void addOrder(LogisticsObject order) {
        if (deliveries == null) {
            deliveries = new ArrayList<>();
        }
        deliveries.add(order);
    }

    public void deleteOrder(LogisticsObject order) {
        deliveries.remove(order);
    }

    public void addOrders(List<LogisticsObject> newOrders) {
        for (LogisticsObject e : newOrders) {
            addOrder(e);
        }
    }

    public void deleteOrders(List<LogisticsObject> toDelete) {
        for (LogisticsObject e : toDelete) {
            deleteOrder(e);
        }
    }

    public List<LogisticsObject> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<LogisticsObject> deliveries) {
        this.deliveries = deliveries;
    }


}
