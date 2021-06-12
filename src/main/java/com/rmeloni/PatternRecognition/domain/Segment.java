package com.rmeloni.PatternRecognition.domain;

import java.util.HashSet;
import java.util.Set;

public class Segment {

    private Set<Point> segmentPoints = new HashSet<>();

    public static void main(String[] args) {
        Segment s = new Segment();
        s.addPoint(new Point(3, 4));
        System.err.println(s.getSegmentPoints());

        s.addPoint(new Point(4, 4));
        System.err.println(s.getSegmentPoints());

        s.addPoint(new Point(3, 4));
        System.err.println(s.getSegmentPoints());
    }

    public void addPoint(Point p) {
        this.segmentPoints.add(p);
    }

    public Set<Point> getSegmentPoints() {
        return segmentPoints;
    }

    public int numberOfPoints() {
        return segmentPoints.size();
    }
}
