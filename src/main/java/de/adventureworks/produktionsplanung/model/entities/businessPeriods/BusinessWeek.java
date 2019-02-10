package de.adventureworks.produktionsplanung.model.entities.businessPeriods;

import java.util.List;
import java.util.Objects;

public class BusinessWeek implements Comparable<BusinessWeek> {

    private List<BusinessDay> days;
    private int calendarWeek;



    public BusinessWeek(List<BusinessDay> days, int calanderWeek) {
        this.calendarWeek = calanderWeek;
        this.days = days;
    }

    public BusinessWeek(){}

    public List<BusinessDay> getDays() {
        return days;
    }

    public void setDays(List<BusinessDay> days) {
        this.days = days;
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

    @Override
    public int compareTo(BusinessWeek o) {
        return calendarWeek -o.calendarWeek;
    }

}
