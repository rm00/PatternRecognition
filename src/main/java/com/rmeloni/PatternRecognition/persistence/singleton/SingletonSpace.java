package com.rmeloni.PatternRecognition.persistence.singleton;

import com.rmeloni.PatternRecognition.domain.Point;
import com.rmeloni.PatternRecognition.domain.SpaceDao;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class SingletonSpace extends HashSet<Point> implements SpaceDao {

    private SingletonSpace() {
    }

    public static SingletonSpace getInstance() {
        return SingletonSpaceHolder.singletonSpace;
    }

    @Override
    public Point addPoint(Point p) {
        this.add(p);
        return p;
    }

    @Override
    public HashSet<Point> getPoints() {
        return this;
    }

    @Override
    public void clearSpace() {
        this.clear();
    }

    private static class SingletonSpaceHolder {
        static final SingletonSpace singletonSpace = new SingletonSpace();
    }

}
