package com.rmeloni.PatternRecognition.service;

import com.rmeloni.PatternRecognition.domain.Point;
import com.rmeloni.PatternRecognition.domain.Space;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class SpaceService {

    @Autowired
    private Space space;

    public Point addPoint(Point p) {
        return space.addPoint(p);
    }

    public HashSet<Point> getPoints() {
        return space.getPoints();
    }

    public void clearSpace() {
        space.clearSpace();
    }

    public int calculateLines(int n) {
//        HashSet<Point> = spaceDao.getPOint();
//        for (x : punti) {
//            segmenti = ....
//
//        }
        return 0;
    }

}
