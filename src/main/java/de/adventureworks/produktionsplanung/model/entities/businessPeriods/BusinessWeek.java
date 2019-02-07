package de.adventureworks.produktionsplanung.model.entities.businessPeriods;

import java.util.List;
import java.util.Objects;

public class BusinessWeek {

    List<BusinessDay> days;

    public BusinessWeek(List<BusinessDay> days) {
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
        return Objects.equals(days, that.days);
    }

    @Override
    public int hashCode() {
        return Objects.hash(days);
    }

    @Override
    public String toString() {
        return "BusinessWeek{" +
                "days=" + days +
                '}';
    }
}
