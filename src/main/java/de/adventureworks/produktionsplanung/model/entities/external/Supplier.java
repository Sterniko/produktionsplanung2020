package de.adventureworks.produktionsplanung.model.entities.external;

import de.adventureworks.produktionsplanung.model.entities.bike.Component;

import java.util.List;
import java.util.Objects;

public class Supplier {

    private String name;
    private int lotSize;
    private int leadTime;
    private Country country;
    private List<Component> components;

    public Supplier(){
        }

    public Supplier(String name, int lotSize, int leadTime, Country country, List<Component> components) {
        this.name = name;
        this.lotSize = lotSize;
        this.leadTime = leadTime;
        this.country = country;
        this.components = components;
    }

    public Supplier(String name, int lotSize, int leadTime, Country country) {
        this.name = name;
        this.lotSize = lotSize;
        this.leadTime = leadTime;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLotSize() {
        return lotSize;
    }

    public void setLotSize(int lotSize) {
        this.lotSize = lotSize;
    }

    public int getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(int leadTime) {
        this.leadTime = leadTime;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<?> components) {
        this.components = (List<Component>) components;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return lotSize == supplier.lotSize &&
                leadTime == supplier.leadTime &&
                Objects.equals(name, supplier.name) &&
                country == supplier.country &&
                Objects.equals(components, supplier.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lotSize, leadTime, country, components);
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "name='" + name + '\'' +
                ", lotSize=" + lotSize +
                ", leadTime=" + leadTime +
                ", country=" + country +
                ", components=" + components +
                '}';
    }
}
