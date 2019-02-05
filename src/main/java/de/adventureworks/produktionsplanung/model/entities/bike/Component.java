package de.adventureworks.produktionsplanung.model.entities.bike;

import de.adventureworks.produktionsplanung.model.entities.external.Supplier;

import java.util.Objects;

public abstract class Component {

    private String name;
    Supplier supplier;

    public Component() {}

    public Component(String name, Supplier supplier) {
        this.name = name;
        this.supplier = supplier;
    }

    public Component(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return Objects.equals(name, component.name) &&
                Objects.equals(supplier, component.supplier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, supplier);
    }

    @Override
    public String toString() {
        return "Component{" +
                "name='" + name + '\'' +
                ", supplier=" + supplier +
                '}';
    }
}
