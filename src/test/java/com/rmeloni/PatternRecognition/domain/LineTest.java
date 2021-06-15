package com.rmeloni.PatternRecognition.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LineTest {

    @Test
    public void Should_CalculateSlopeAndInterceptAsNumbers_When_StraightLineIsNotVertical() {
        Point p = new Point(1, 1);
        Point q = new Point(2, 4);
        Line l = Line.fromPointsCouple(p, q);
        Assertions.assertEquals(3, l.getSlope(), 0);
        Assertions.assertEquals(-2, l.getvAxisIntercept(), 0);
        Assertions.assertEquals(2 / 3., l.gethAxisIntercept(), 0);
    }

    @Test
    public void Should_CalculateSlopeAndInterceptAsSpecialCases_When_StraightLineIsVertical() {
        Point p = new Point(3, 4);
        Point q = new Point(3, 7);
        Line l = Line.fromPointsCouple(p, q);
        Assertions.assertEquals(Double.POSITIVE_INFINITY, l.getSlope(), 0);
        Assertions.assertEquals(Double.NaN, l.getvAxisIntercept(), 0);
        Assertions.assertEquals(3, l.gethAxisIntercept(), 0);
    }

    @Test
    public void Should_CalculateUndefinedStraightLine_When_PointsAreCoincident() {
        Point p = new Point(3, 4);
        Point q = new Point(3, 4);
        Line l = Line.fromPointsCouple(p, q);
        Assertions.assertEquals(Double.NaN, l.getSlope(), 0);
        Assertions.assertEquals(Double.NaN, l.getvAxisIntercept(), 0);
        Assertions.assertEquals(Double.NaN, l.gethAxisIntercept(), 0);
    }
}