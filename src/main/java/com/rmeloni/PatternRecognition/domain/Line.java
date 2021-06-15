package com.rmeloni.PatternRecognition.domain;

import java.util.Objects;

/**
 * A Line is defined by a slope (double), a vertical-axis intercept (double) and a horizontal-axis intercept (double).
 */
public class Line {
    private double slope;
    private double vAxisIntercept;
    private double hAxisIntercept;

    /**
     * Creates a Line which crosses the points p and q.
     *
     * @param p can be any valid Point in the space
     * @param q can be any valid Point in the space
     * @return a Line. There are three cases:
     * If points are coincident, slope and intercepts are NaN;
     * If points are on a vertical line, slope is positive-infinite, vertical intercept is NaN and horizontal intercept is finite;
     * If points are on a horizontal line, slope is zero, vertical intercept is finite and horizontal intercept is NaN;
     * If points are on a diagonal line, slope and intercept are finite numbers.
     */
    public static Line fromPointsCouple(Point p, Point q) {
        Line l = new Line();
        double slope, vInt, hInt;
        if ((q.getX() - p.getX()) == 0) {
            if ((q.getY() - p.getY()) == 0) {
                slope = Double.NaN;
                hInt = Double.NaN;
            } else {
                slope = Double.POSITIVE_INFINITY;
                hInt = q.getX();
            }
            vInt = Double.NaN;
        } else {
            slope = (q.getY() - p.getY()) / (q.getX() - p.getX());
            vInt = q.getY() - (slope * q.getX());
            if (slope == 0) {
                hInt = Double.NaN;
            } else {
                hInt = -vInt / slope;
            }
        }
        l.setSlope(slope);
        l.setvAxisIntercept(vInt);
        l.sethAxisIntercept(hInt);
        return l;
    }

    @Override
    public String toString() {
        return "Line{" +
                "slope=" + slope +
                ", vAxisIntercept=" + vAxisIntercept +
                ", hAxisIntercept=" + hAxisIntercept +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Double.compare(line.slope, slope) == 0 && Double.compare(line.vAxisIntercept, vAxisIntercept) == 0 && Double.compare(line.hAxisIntercept, hAxisIntercept) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(slope, vAxisIntercept, hAxisIntercept);
    }

    public double getSlope() {
        return slope;
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    public double getvAxisIntercept() {
        return vAxisIntercept;
    }

    public void setvAxisIntercept(double vAxisIntercept) {
        this.vAxisIntercept = vAxisIntercept;
    }

    public double gethAxisIntercept() {
        return hAxisIntercept;
    }

    public void sethAxisIntercept(double hAxisIntercept) {
        this.hAxisIntercept = hAxisIntercept;
    }

}
