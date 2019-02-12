package de.adventureworks.produktionsplanung.controller.ship;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UpdateShipRequest {

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate newArrival;

    public UpdateShipRequest() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getNewArrival() {
        return newArrival;
    }

    public void setNewArrival(LocalDate newArrival) {
        this.newArrival = newArrival;
    }

    @Override
    public String toString() {
        return "UpdateShipRequest{" +
                "name='" + name + '\'' +
                ", newArrival=" + newArrival +
                '}';
    }
}
