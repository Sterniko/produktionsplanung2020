package de.adventureworks.produktionsplanung.model.entities.external;

import java.time.LocalDate;
import java.util.Objects;

public class Ship implements Comparable<Ship>{

    private String name;
    private LocalDate departure;
    private LocalDate arrival;

    public Ship(String name, LocalDate departure, LocalDate arrival) {
        this.name = name;
        this.departure = departure;
        this.arrival = arrival;
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
}
