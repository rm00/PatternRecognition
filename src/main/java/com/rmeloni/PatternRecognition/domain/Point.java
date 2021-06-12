package com.rmeloni.PatternRecognition.domain;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Point {

    @NotNull
    private Double x;

    @NotNull
    private Double y;

    public Point() {
    }

    /**
     * Creates a Point with the specified coordinates
     *
     * @param x is the x-coordinate
     * @param y is the y-coordinate
     */
    public Point(double x, double y) {
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
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
