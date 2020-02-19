package de.adventureworks.produktionsplanung.controller.ship;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UpdateShipRequest {

    private String name;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
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

   // public void setNewArrival(LocalDate newArrival) {

    //    this.newArrival = newArrival;
    //}
    public void setNewArrival(String newArrival){
        int ende = -1;
        int anfang = -1;
        ende = newArrival.length() ;
        anfang = newArrival.length() - 2;
        System.out.println("here");
        int tag = Integer.parseInt(newArrival.substring(anfang, ende));
        ende = newArrival.length() - 3;
        anfang = newArrival.length() - 5;
        System.out.println("here");
        int month = Integer.parseInt(newArrival.substring(anfang, ende));
        ende = newArrival.length() - 6;
        anfang = newArrival.length() - 10;
        System.out.println("here");
        int jahr = Integer.parseInt(newArrival.substring(anfang, ende));
        this.newArrival = LocalDate.of(jahr, month, tag);
    }

    @Override
    public String toString() {
        return "UpdateShipRequest{" +
                "name='" + name + '\'' +
                ", newArrival=" + newArrival +
                '}';
    }
}
