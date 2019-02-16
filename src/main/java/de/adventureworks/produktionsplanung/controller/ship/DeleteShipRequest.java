package de.adventureworks.produktionsplanung.controller.ship;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class DeleteShipRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deleteDate;
    private String name;

    public DeleteShipRequest() {

    }

    public LocalDate getDeleteDate() {
        return deleteDate;
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
