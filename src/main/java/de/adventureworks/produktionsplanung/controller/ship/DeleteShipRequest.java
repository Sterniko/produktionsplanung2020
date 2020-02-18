package de.adventureworks.produktionsplanung.controller.ship;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class DeleteShipRequest {

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate deleteDate;
    private String name;

    public DeleteShipRequest() {

    }

    public LocalDate getDeleteDate() {
        return deleteDate;
    }

    public String getGermanDeleteDate(){
        String returnvalue = String.format("%02d.%02d.%d", deleteDate.getDayOfMonth(), deleteDate.getMonthValue(), deleteDate.getYear());
        return returnvalue;
    }

    public void setDeleteDate(LocalDate deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
