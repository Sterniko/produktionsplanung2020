package de.adventureworks.produktionsplanung.model.entities.bike;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bike {

    private String name;
    private Component frame;
    private Component fork;
    private Component saddle;

    public Bike() {
    }

    public Bike(String name, Component frame, Component fork, Component saddle) {
        this.name = name;
        this.frame = frame;
        this.fork = fork;
        this.saddle = saddle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Component getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public Component getFork() {
        return fork;
    }

    public void setFork(Fork fork) {
        this.fork = fork;
    }

    public Component getSaddle() {
        return saddle;
    }

    public void setSaddle(Saddle saddle) {
        this.saddle = saddle;
    }

    public List<Component> getComponents() {
        List<Component> components = new ArrayList<>();
        components.add(fork);
        components.add(frame);
        components.add(saddle);
        return components;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bike bike = (Bike) o;
        return Objects.equals(name, bike.name) &&
                Objects.equals(frame, bike.frame) &&
                Objects.equals(fork, bike.fork) &&
                Objects.equals(saddle, bike.saddle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, frame, fork, saddle);
    }

    @Override
    public String toString() {
        return "Bike{" +
                "name='" + name + '\'' +
                ", frame=" + frame +
                ", fork=" + fork +
                ", saddle=" + saddle +
                '}';
    }
}
