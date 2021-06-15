package com.rmeloni.PatternRecognition.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        double slope, vIntercept, hIntercept;
        BigDecimal x1 = BigDecimal.valueOf(p.getX());
        BigDecimal x2 = BigDecimal.valueOf(q.getX());
        BigDecimal y1 = BigDecimal.valueOf(p.getY());
        BigDecimal y2 = BigDecimal.valueOf(q.getY());

        BigDecimal dX = x2.subtract(x1);
        BigDecimal dY = y2.subtract(y1);
        if (dX.compareTo(BigDecimal.ZERO) == 0) {
            if (dY.compareTo(BigDecimal.ZERO) == 0) {
                slope = Double.NaN;
                hIntercept = Double.NaN;
            } else {
                slope = Double.POSITIVE_INFINITY;
                hIntercept = q.getX();
            }
            vIntercept = Double.NaN;
        } else {
            if (dY.compareTo(BigDecimal.ZERO) == 0) {
                slope = 0.;
                vIntercept = q.getY();
                hIntercept = Double.NaN;
            } else {
                slope = dY.divide(dX, 10, RoundingMode.HALF_DOWN).doubleValue();
                vIntercept = q.getY() - (slope * q.getX());
                hIntercept = -vIntercept / slope;
            }
        }
        l.setSlope(slope);
        l.setvAxisIntercept(vIntercept);
        l.sethAxisIntercept(hIntercept);
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
