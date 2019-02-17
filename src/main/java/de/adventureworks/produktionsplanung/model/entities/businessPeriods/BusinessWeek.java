package de.adventureworks.produktionsplanung.model.entities.businessPeriods;

import de.adventureworks.produktionsplanung.model.entities.bike.Bike;

import java.util.*;

public class BusinessWeek implements Comparable<BusinessWeek> {

    private List<BusinessDay> days;
    private int calendarWeek;
    private Map<Bike, Integer> plannedProduction;



    private int year;


    private int workingHours;

    public BusinessWeek(List<BusinessDay> days, int calendarWeek, Map<Bike, Integer> plannedProduction, int workingHours) {
        this.days = days;
        this.calendarWeek = calendarWeek;
        this.plannedProduction = plannedProduction;
        this.workingHours = workingHours;
        Collections.sort(days);
    }

    public BusinessWeek(){
        days = new ArrayList<>();
        plannedProduction = new HashMap<>();
    }

    public BusinessWeek(int calendarWeek) {
        this();
        this.calendarWeek = calendarWeek;
    }


    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public List<BusinessDay> getDays() {
        return days;
    }

    public void setDays(List<BusinessDay> days) {
        this.days = days;
        Collections.sort(days);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessWeek that = (BusinessWeek) o;
        return this.calendarWeek==that.calendarWeek;
    }

    @Override
    public int hashCode() {
        return calendarWeek;
    }

    @Override
    public String toString() {
        return "BusinessWeek{" +
                "days=" + days +
                '}';
    }

    public int getCalendarWeek() {
        return calendarWeek;
    }

    public void setCalendarWeek(int calendarWeek) {
        this.calendarWeek = calendarWeek;
    }

    public Map<Bike, Integer> getPlannedProduction() {
        return plannedProduction;
    }

    public void setPlannedProduction(Map<Bike, Integer> plannedProduction) {
        this.plannedProduction = plannedProduction;
    }

    public BusinessDay getEarliestWorkingDay() {
        //TODO checken nach Arbeitstagen
        return this.days.get(0);
    }

    public Integer getSumOfPlannedProduction() {
        Integer sum = 0;
        for (BusinessDay day: days) {
            sum += day.getSumOfPlannedDailyProduction();
        }
        return sum;
    }

    public Integer getSumOfActualProduction() {
        Integer sum = 0;
        for (BusinessDay day: days) {
            sum += day.getSumOfActualDailyProduction();
        }
        return sum;
    }

    @Override
    public int compareTo(BusinessWeek o) {
        return calendarWeek -o.calendarWeek;
    }

}
