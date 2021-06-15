package com.rmeloni.PatternRecognition.domain;

import com.rmeloni.PatternRecognition.domain.events.PointAddedEventPublisher;
import com.rmeloni.PatternRecognition.domain.events.SpaceClearedEventPublisher;
import com.rmeloni.PatternRecognition.persistence.SpaceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Space<br>
 * - handles the persistence of the Points;<br>
 * - fires PointAddedEvents and SpaceClearedEvents, which are listened to by a CollinearPointsCalculator
 */
@Component
public class Space {

    @Autowired
    @Qualifier("memDBSpace")
    private SpaceDao spaceDao;

    @Autowired
    private PointAddedEventPublisher pointAddedEventPublisher;

    @Autowired
    private SpaceClearedEventPublisher spaceClearedEventPublisher;

    /**
     * Adds a Point to the Space and returns the Point saved;
     * if p is an unknown Point, it publishes also a PointAddedEvent.
     *
     * @param p is a Point
     * @return the Point saved
     */
    public Point addPoint(Point p) {
        Point savedPoint;
        if (!this.getPoints().contains(p)) {
            savedPoint = spaceDao.addPoint(p);
            pointAddedEventPublisher.publish(savedPoint);
        } else {
            savedPoint = p;
        }
        return savedPoint;
    }

    public Set<Point> getPoints() {
        return spaceDao.getPoints();
    }

    public void clearSpace() {
        spaceDao.clearSpace();
        spaceClearedEventPublisher.publish();
    }

}
