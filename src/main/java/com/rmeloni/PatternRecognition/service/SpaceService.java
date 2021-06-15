package com.rmeloni.PatternRecognition.service;

import com.rmeloni.PatternRecognition.domain.Point;
import com.rmeloni.PatternRecognition.domain.Segment;
import com.rmeloni.PatternRecognition.domain.Space;
import com.rmeloni.PatternRecognition.domain.metrics.CollinearPoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SpaceService {

    private static final Logger logger = LoggerFactory.getLogger(SpaceService.class);

    @Autowired
    private Space space;

    @Autowired
    private CollinearPoints collinearPoints;

    public Point addPoint(Point p) {
        logger.info("Adding Point " + p.toString());
        return space.addPoint(p);
    }

    public Set<Point> getPoints() {
        logger.info("Getting Points from Space");
        Set<Point> s = space.getPoints();
        logger.info("Found: " + s.toString());
        return s;
    }

    public void clearSpace() {
        logger.info("Clearing the Space");
        space.clearSpace();
    }

    public List<Segment> getListOfSegmentsWithAtLeastNCollinearPoints(Integer n) {
        logger.info("Getting List of Segments with at least " + n + " collinear Points");
        List<Segment> l = collinearPoints.getListOfSegmentsWithAtLeastNCollinearPoints(n);
        logger.info("Found: " + l.stream().map(Segment::toString).map(toString -> toString + "\n").collect(Collectors.joining()));
        return l;
    }

}
