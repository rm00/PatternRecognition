package com.rmeloni.PatternRecognition.persistence;

import com.rmeloni.PatternRecognition.domain.Point;

import java.util.Set;

public interface SpaceDao {

    Point addPoint(Point p);

    Set<Point> getPoints();

    void clearSpace();
}
