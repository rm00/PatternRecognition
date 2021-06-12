package com.rmeloni.PatternRecognition.persistence.memDB;

import com.rmeloni.PatternRecognition.domain.Point;

public class MemDBConverter {

    public static MemDBPoint fromPoint(Point point) {
        return new MemDBPoint(point.getX(), point.getY());
    }

    public static Point toPoint(MemDBPoint memDBPoint) {
        return new Point(memDBPoint.getX(), memDBPoint.getY());
    }
}
