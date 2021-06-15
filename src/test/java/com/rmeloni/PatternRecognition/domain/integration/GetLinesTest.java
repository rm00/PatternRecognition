package com.rmeloni.PatternRecognition.domain.integration;

import com.rmeloni.PatternRecognition.domain.Line;
import com.rmeloni.PatternRecognition.domain.Point;
import com.rmeloni.PatternRecognition.domain.Segment;
import com.rmeloni.PatternRecognition.domain.Space;
import com.rmeloni.PatternRecognition.domain.metrics.CollinearPoints;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

@SpringBootTest
@RunWith(SpringRunner.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GetLinesTest {

    @Autowired
    private Space space;

    @Autowired
    private CollinearPoints collinearPoints;

    @Test
    public void Should_CalculateAllLines() {
        space.clearSpace();
        Point p1 = new Point(1, 3);
        Point p2 = new Point(1, 4);
        Point p3 = new Point(1, 5);
        Point p4 = new Point(3, 6);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(4, 4);
        Point p7 = new Point(5, 5);
        Point p8 = new Point(6, 6);
        space.addPoint(p1);
        space.addPoint(p2);
        space.addPoint(p3);
        space.addPoint(p4);
        space.addPoint(p5);
        space.addPoint(p6);
        space.addPoint(p7);

        Line l1 = Line.fromPointsCouple(p1, p2);
        Segment s1ThreePoints = new Segment(l1, new HashSet<>() {{
            add(p1);
            add(p2);
            add(p3);
        }});

        Line l2 = Line.fromPointsCouple(p6, p7);
        Segment s2ThreePoints = new Segment(l2, new HashSet<>() {{
            add(p5);
            add(p6);
            add(p7);
        }});

        Segment s3FourPoints = new Segment(l2, new HashSet<>() {{
            add(p5);
            add(p6);
            add(p7);
            add(p8);
        }});

        Assert.assertEquals(2, collinearPoints.getListOfSegmentsWithAtLeastNCollinearPoints(3).size());
        Assert.assertTrue(collinearPoints.getListOfSegmentsWithAtLeastNCollinearPoints(3).contains(s1ThreePoints));
        Assert.assertTrue(collinearPoints.getListOfSegmentsWithAtLeastNCollinearPoints(3).contains(s2ThreePoints));
        Assert.assertEquals(0, collinearPoints.getListOfSegmentsWithAtLeastNCollinearPoints(4).size());

        space.addPoint(p8);
        Assert.assertEquals(2, collinearPoints.getListOfSegmentsWithAtLeastNCollinearPoints(3).size());
        Assert.assertEquals(1, collinearPoints.getListOfSegmentsWithAtLeastNCollinearPoints(4).size());
        Assert.assertTrue(collinearPoints.getListOfSegmentsWithAtLeastNCollinearPoints(4).contains(s3FourPoints));
    }
}