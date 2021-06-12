package com.rmeloni.PatternRecognition.domain;

import java.util.HashSet;

public interface SpaceDao {

    Point addPoint(Point p);

    HashSet<Point> getPoints();

    void clearSpace();
}
