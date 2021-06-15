package com.rmeloni.PatternRecognition.domain;

import com.rmeloni.PatternRecognition.domain.events.PointAddedEventPublisher;
import com.rmeloni.PatternRecognition.domain.events.SpaceClearedEventPublisher;
import com.rmeloni.PatternRecognition.persistence.SpaceDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = Space.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpaceTest {

    @Autowired
    private Space space;

    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "memDBSpace")
    private SpaceDao spaceDao;

    @MockBean
    private PointAddedEventPublisher pointAddedEventPublisher;

    @MockBean
    private SpaceClearedEventPublisher spaceClearedEventPublisher;

    @Test
    public void Should_SaveNewPoints_When_PointIsValid() {
        Point p = new Point(3, 4);
        Point q = new Point(4, 5);
        Set<Point> firstSet = new HashSet<>() {{
            add(p);
        }};
        Set<Point> secondSet = new HashSet<>() {{
            add(p);
            add(q);
        }};

        Mockito.doNothing().when(pointAddedEventPublisher).publish(Mockito.any(Point.class));
        Mockito.doNothing().when(spaceClearedEventPublisher).publish();
        Mockito.when(spaceDao.addPoint(p)).thenReturn(p);
        assertEquals(p, space.addPoint(p));

        Mockito.when(spaceDao.getPoints()).thenReturn(firstSet);
        assertEquals(firstSet, space.getPoints());
        assertTrue(space.getPoints().contains(p));
        assertEquals(1, space.getPoints().size());

        Mockito.when(spaceDao.addPoint(q)).thenReturn(q);
        Mockito.when(spaceDao.getPoints()).thenReturn(secondSet);
        assertTrue(space.getPoints().contains(q));
        assertTrue(space.getPoints().contains(p));
        assertEquals(2, space.getPoints().size());
    }

    @Test
    public void ShouldNot_SaveNewPoints_When_PointIsCoincident() {
        Point p = new Point(3, 4);
        Point q = new Point(3, 4);
        Set<Point> firstSet = new HashSet<>() {{
            add(p);
        }};

        Set<Point> secondSet = new HashSet<>() {{
            add(p);
            add(q);
        }};

        Mockito.doNothing().when(pointAddedEventPublisher).publish(Mockito.any(Point.class));
        Mockito.doNothing().when(spaceClearedEventPublisher).publish();
        Mockito.when(spaceDao.addPoint(p)).thenReturn(p);
        Mockito.when(spaceDao.addPoint(q)).thenReturn(q);
        space.addPoint(p);
        Mockito.when(spaceDao.getPoints()).thenReturn(firstSet);

        space.addPoint(q);
        Mockito.when(spaceDao.getPoints()).thenReturn(secondSet);

        assertTrue(space.getPoints().contains(q));
        assertTrue(space.getPoints().contains(p));
        assertEquals(1, space.getPoints().size());
    }

    @Test
    public void Should_ReturnEmptySet_When_SpaceIsCleared() {
        space.clearSpace();
        Mockito.verify(spaceDao, times(1)).clearSpace();
        Mockito.when(spaceDao.getPoints()).thenReturn(new HashSet<>());
        assertEquals(0, space.getPoints().size());
    }

}