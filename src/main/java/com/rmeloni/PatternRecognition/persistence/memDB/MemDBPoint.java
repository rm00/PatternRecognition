package com.rmeloni.PatternRecognition.persistence.memDB;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "Point")
@IdClass(MemDBPointPK.class)
public class MemDBPoint {

    @Id
    @NotNull
    private Double x;

    @Id
    @NotNull
    private Double y;

    public MemDBPoint() {
    }

    /**
     * Creates a Point with the specified coordinates
     *
     * @param x is the x-coordinate
     * @param y is the y-coordinate
     */
    public MemDBPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemDBPoint that = (MemDBPoint) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }
}
