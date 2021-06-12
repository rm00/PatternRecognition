package com.rmeloni.PatternRecognition.domain;

import com.rmeloni.PatternRecognition.persistence.singleton.SingletonSpace;
import org.junit.Assert;
import org.junit.Test;


public class SingletonSpaceTest {

    @Test
    public void singletonSpaceIsIndeedASingleton() {
        SingletonSpace space1 = SingletonSpace.getInstance();
        space1.addPoint(new Point(3, 4));
        SingletonSpace space2 = SingletonSpace.getInstance();
        Assert.assertEquals(space2.size(), 1);
    }

    @Test
    public void singletonSpaceSavesCorrectlyNewPoints() {
        SingletonSpace space = SingletonSpace.getInstance();
        space.addPoint(new Point(3, 4));
        Assert.assertEquals(space.size(), 1);
        Assert.assertEquals(space.getPoints()
                        .stream()
                        .findAny()
                        .get(),
                new Point(3, 4));
    }

    @Test
    public void singletonSpaceDoesNotSaveDuplicates() {
        SingletonSpace space = SingletonSpace.getInstance();
        Point p = new Point(3, 4);
        space.addPoint(p);
        space.addPoint(p);
        Assert.assertEquals(space.size(), 1);
    }

    @Test
    public void singletonSpaceDeletesCorrectlyAllPoints() {
        SingletonSpace space = SingletonSpace.getInstance();
        space.addPoint(new Point(3, 4));
        space.addPoint(new Point(5, 2));
        Assert.assertEquals(space.size(), 2);
        space.clearSpace();
        Assert.assertEquals(space.size(), 0);
    }
}