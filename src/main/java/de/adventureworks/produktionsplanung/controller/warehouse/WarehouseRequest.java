package de.adventureworks.produktionsplanung.controller.warehouse;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class WarehouseRequest {

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    private Map<String, Integer> warehouseMap;

    public WarehouseRequest(String date, Map<String, Integer> warehouseMap) {
        int ende = -1;
        int anfang = -1;
        ende = date.length();
        anfang = date.length() - 2;
        System.out.println("here");
        int tag = Integer.parseInt(date.substring(anfang, ende));
        ende = date.length() - 3;
        anfang = date.length() - 5;
        System.out.println("here");
        int month = Integer.parseInt(date.substring(anfang, ende));
        ende = date.length() - 6;
        anfang = date.length() - 10;
        System.out.println("here");
        int jahr = Integer.parseInt(date.substring(anfang, ende));
        this.date = LocalDate.of(jahr, month, tag);
        this.warehouseMap = warehouseMap;
    }

    public WarehouseRequest() {
        warehouseMap = new HashMap<>();
    }

    public LocalDate getDate() {
        return date;
    }

    //public void setDate(LocalDate date) {
    //   this.date = date;
    //  }

    public void setDate(String date) {
        System.out.println(date);
        int ende = -1;
        int anfang = -1;

        ende = date.length();
        anfang = date.length() - 2;
        String sub = date.substring(anfang, ende);
        System.out.println(sub);
        int tag = Integer.parseInt(sub);

        ende = date.length() - 3;
        anfang = date.length() - 5;
        sub = date.substring(anfang, ende);
        System.out.println(sub);//01
        int month = Integer.parseInt(sub);

        ende = date.length() - 6;
        anfang = date.length() - 10;
        sub = date.substring(anfang, ende);
        System.out.println(sub);//1-
        int jahr = Integer.parseInt(sub);


        this.date = LocalDate.of(jahr, month, tag);
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
