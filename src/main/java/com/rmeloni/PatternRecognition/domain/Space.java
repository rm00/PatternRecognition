package com.rmeloni.PatternRecognition.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class Space {

    private HashSet<Point> points = new HashSet<>();
    private List<Segment> segments = new ArrayList<>();

    @Autowired
    @Qualifier("singletonSpace")
    private SpaceDao spaceDao;

    public Point addPoint(Point p) {
        Point savedPoint = spaceDao.addPoint(p);
        points.add(savedPoint);
        this.updateSegments();
        return savedPoint;
    }

    public HashSet<Point> getPoints() {
        return points;
    }

    public void clearSpace() {
        spaceDao.clearSpace();
        points = new HashSet<>();
        segments = new ArrayList<>();
    }

    private void updateSegments() {
    }


}
