package com.rmeloni.PatternRecognition.persistence.singleton;

import com.rmeloni.PatternRecognition.domain.Point;
import com.rmeloni.PatternRecognition.persistence.SpaceDao;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SingletonSpace implements SpaceDao {

    private final Set<Point> points;

    private SingletonSpace() {
        this.points = new HashSet<>();
    }

    public static SingletonSpace getInstance() {
        return SingletonSpaceHolder.singletonSpace;
    }

    @Override
    public Point addPoint(Point p) {
        this.points.add(p);
        return p;
    }

    @Override
    public Set<Point> getPoints() {
        return this.points;
    }

    @Override
    public void clearSpace() {
        this.points.clear();
    }

    private static class SingletonSpaceHolder {
        static final SingletonSpace singletonSpace = new SingletonSpace();
    }

    public int size() {
        return this.points.size();
    }
}
