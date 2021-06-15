package com.rmeloni.PatternRecognition.persistence;

import com.rmeloni.PatternRecognition.domain.Point;
import com.rmeloni.PatternRecognition.persistence.singleton.SingletonSpace;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SingletonSpaceTest {

    @Test
    public void Should_ReturnPreviousInstance_When_InstantiatedTwice() {
        SingletonSpace space1 = SingletonSpace.getInstance();
        space1.addPoint(new Point(3, 4));
        SingletonSpace space2 = SingletonSpace.getInstance();
        assertEquals(space2.size(), 1);
    }

    @Test
    public void Should_SaveNewPoints_When_InputIsValid() {
        SingletonSpace space = SingletonSpace.getInstance();
        space.addPoint(new Point(3, 4));
        assertEquals(1, space.size());
        assertEquals(new Point(3, 4), space.getPoints()
                .stream()
                .findAny()
                .get());
    }

    @Test
    public void ShouldNot_SaveDuplicates() {
        SingletonSpace space = SingletonSpace.getInstance();
        Point p = new Point(3, 4);
        Point q = new Point(3, 4);
        space.addPoint(p);
        space.addPoint(q);
        assertEquals(1, space.size());
    }

    @Test
    public void Should_ClearPoints_When_ClearSpaceIsInvoked() {
        SingletonSpace space = SingletonSpace.getInstance();
        space.addPoint(new Point(3, 4));
        space.addPoint(new Point(5, 2));
        assertEquals(2, space.size());
        space.clearSpace();
        assertEquals(0, space.size());
    }
}