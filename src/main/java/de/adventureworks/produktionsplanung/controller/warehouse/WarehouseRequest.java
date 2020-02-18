package de.adventureworks.produktionsplanung.controller.warehouse;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class WarehouseRequest {

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    private Map<String, Integer> warehouseMap;

    public WarehouseRequest(LocalDate date, Map<String, Integer> warehouseMap) {
        this.date = date;
        this.warehouseMap = warehouseMap;
    }

    public WarehouseRequest() {
        warehouseMap= new HashMap<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<String, Integer> getWarehouseMap() {
        return warehouseMap;
    }

    public void setWarehouseMap(Map<String, Integer> warehouseMap) {
        this.warehouseMap = warehouseMap;
    }

    public void setComponentAmout(String component, int amount) {
      //  System.out.println(component);
      //  System.out.println(amount);
    }

    @Override
    public String toString() {
        return "WarehouseRequest{" +
                "date=" + date +
                ", warehouseMap=" + warehouseMap +
                '}';
    }
}
