package de.adventureworks.produktionsplanung.model.entities.bike;

import java.util.Objects;

public class Bike {

    private String name;
    private Frame frame;
    private Fork fork;
    private  Saddle saddle;

    public Bike(String name, Frame frame, Fork fork, Saddle saddle) {
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

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public Fork getFork() {
        return fork;
    }

    public void setFork(Fork fork) {
        this.fork = fork;
    }

    public Saddle getSaddle() {
        return saddle;
    }

    public void setSaddle(Saddle saddle) {
        this.saddle = saddle;
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
