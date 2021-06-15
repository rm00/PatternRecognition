package com.rmeloni.PatternRecognition.domain.metrics;

import com.rmeloni.PatternRecognition.domain.Line;
import com.rmeloni.PatternRecognition.domain.Point;
import com.rmeloni.PatternRecognition.domain.Segment;
import com.rmeloni.PatternRecognition.domain.Space;
import com.rmeloni.PatternRecognition.domain.events.PointAddedEvent;
import com.rmeloni.PatternRecognition.domain.events.SpaceClearedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * SharedLinesCollinearPointsCalculator finds the Segments crossing the Points in the Space.<br>
 * It does that by means of a Map&lt;Line,Set&lt;Point&gt;&gt;.<br>
 * This Map is calculated the first time the method getLines() is called. After that moment, the addition of a new Point
 * fires a PointAddedEvent, which in turn triggers the update of the Map.<br>
 * If Space.clearSpace() is called, the state of the Map is reset.
 */
@Component
public class SharedLinesCollinearPoints implements CollinearPoints {

    private static final Logger logger = LoggerFactory.getLogger(SharedLinesCollinearPoints.class);
    private final Map<Line, Set<Point>> linesToPoints = new ConcurrentHashMap<>();
    private final AtomicBoolean mapIsInitialized = new AtomicBoolean(false);

    @Autowired
    private Space space;

    /**
     * Finds the List of Segments which have n or more collinear Points.
     *
     * @param n is the minimum number of Points belonging to a Segment
     * @return a List&lt;Segment&gt;
     */
    @Override
    public List<Segment> getListOfSegmentsWithAtLeastNCollinearPoints(Integer n) {
        if (n > space.getPoints().size()) {
            return new ArrayList<>();   // no need to filter the Map: the solution is impossible
        }

        if (mapIsInitialized.compareAndSet(false, true)) {
            initMap();
            logger.info("Map initialized");
        }

        return this.linesToPoints.entrySet()
                .stream()
                .filter(e -> e.getValue().size() >= n)
                .map(e -> new Segment(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * If a SpaceClearedEvent is fired, the state is reset.
     */
    @EventListener(SpaceClearedEvent.class)
    private synchronized void clearMapAndReset() {
        linesToPoints.clear();
        mapIsInitialized.set(false);
        logger.info("Map cleared; state reset");
    }

    /**
     * If a PointAddedEvent is fired, it updates the Map only if the Map is initialized.
     * This way, the computational cost for the addition of the Points before the first call of getLines() is constant.
     *
     * @param pointAddedEvent is the PointAddedEvent fired
     */
    @EventListener(PointAddedEvent.class)
    private synchronized void conditionallyUpdateMap(PointAddedEvent pointAddedEvent) {
        if (mapIsInitialized.get()) {
            this.updateMap(pointAddedEvent.getPointAdded());
            logger.info("Map updated");
        }   // else do nothing
    }

    /**
     * For each couple of distinct Points, it finds the Line on which the couple lies and assigns the couple to
     * that Line through the Map.
     *
     */
    private void initMap() {
        Set<Point> startingPoints = space.getPoints();
        Set<Point> endingPoints = new HashSet<>(startingPoints);
        this.linesToPoints.clear();

        for (Point p : startingPoints) {
            endingPoints.remove(p);
            for (Point q : endingPoints) {
                Line l = Line.fromPointsCouple(p, q);
                linesToPoints.computeIfAbsent(l, x -> new HashSet<>());
                linesToPoints.get(l).add(p);    // could be already there
                linesToPoints.get(l).add(q);
            }
        }
    }

    /**
     * The Map is updated by calculating only the connections between the new Point p and the already-present Points
     *
     * @param p is the new added Point
     */
    private void updateMap(Point p) {
        Set<Point> endingPoints = space.getPoints();
        endingPoints.remove(p); // p was already saved

        for (Point q : endingPoints) {
            Line l = Line.fromPointsCouple(p, q);
            linesToPoints.computeIfAbsent(l, x -> new HashSet<>());
            linesToPoints.get(l).add(p);
            linesToPoints.get(l).add(q);
        }
    }
}
