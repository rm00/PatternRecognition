package com.rmeloni.PatternRecognition.domain;

import java.util.Set;

/**
 * A Segment is a Line which contains a Set of Points.
 */
public class Segment extends Line {

    private Set<Point> points;

    /**
     * Creates a Segment, given the Line it lies on and a Set of Points
     *
     * @param l is the Line on which the Segment lies
     * @param p is a Set of Points
     */
    public Segment(Line l, Set<Point> p) {
        super();
        this.setSlope(l.getSlope());
        this.setvAxisIntercept(l.getvAxisIntercept());
        this.sethAxisIntercept(l.gethAxisIntercept());
        this.points = p;
    }

    public Segment() {
    }

    public Set<Point> getPoints() {
        return points;
    }

    public void setPoints(Set<Point> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "points=" + points +
                "} " + super.toString();
    }
}
