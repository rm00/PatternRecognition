package com.rmeloni.PatternRecognition.domain.events;

import com.rmeloni.PatternRecognition.domain.Point;

public class PointAddedEvent {
    private final Point pointAdded;

    public PointAddedEvent(Point pointAdded) {
        this.pointAdded = pointAdded;
    }

    public Point getPointAdded() {
        return pointAdded;
    }
}
