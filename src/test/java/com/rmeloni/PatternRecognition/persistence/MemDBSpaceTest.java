package com.rmeloni.PatternRecognition.persistence;

import com.rmeloni.PatternRecognition.domain.Point;
import com.rmeloni.PatternRecognition.persistence.memDB.MemDBConverter;
import com.rmeloni.PatternRecognition.persistence.memDB.MemDBPoint;
import com.rmeloni.PatternRecognition.persistence.memDB.MemDBRepository;
import com.rmeloni.PatternRecognition.persistence.memDB.MemDBSpace;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = MemDBSpace.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class MemDBSpaceTest {

    @Autowired
    private MemDBSpace memDBSpace;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemDBRepository memDBRepository;

    @Test
    void Should_SaveNewPoints_When_InputIsValid() {
        Point p = new Point(3, 4);
        List<MemDBPoint> firstList = new ArrayList<>() {{
            add(MemDBConverter.fromPoint(p));
        }};
        Set<Point> firstSet = new HashSet<>() {{
            add(p);
        }};

        MemDBPoint dbP = MemDBConverter.fromPoint(p);
        Mockito.when(memDBRepository.save(dbP)).thenReturn(dbP);
        assertEquals(p, memDBSpace.addPoint(p));

        Mockito.when(memDBRepository.findAll()).thenReturn(firstList);
        assertEquals(firstSet, memDBSpace.getPoints());
        assertTrue(memDBSpace.getPoints().contains(p));
        assertEquals(1, memDBSpace.getPoints().size());
    }

    @Test
    void ShouldNot_SaveDuplicates() {
        Point p = new Point(3, 4);
        Point q = new Point(3, 4);
        List<MemDBPoint> firstList = new ArrayList<>() {{
            add(MemDBConverter.fromPoint(p));
            add(MemDBConverter.fromPoint(q));
        }};
        Set<Point> firstSet = new HashSet<>() {{
            add(p);
        }};

        MemDBPoint dbP = MemDBConverter.fromPoint(p);
        MemDBPoint dbQ = MemDBConverter.fromPoint(q);
        Mockito.when(memDBRepository.save(dbP)).thenReturn(dbP);
        Mockito.when(memDBRepository.save(dbQ)).thenReturn(dbQ);
        Mockito.when(memDBRepository.findAll()).thenReturn(firstList);
        memDBSpace.addPoint(p);
        memDBSpace.addPoint(q);
        assertEquals(1, memDBSpace.getPoints().size());

    }

}