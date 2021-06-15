package com.rmeloni.PatternRecognition.persistence;

import com.rmeloni.PatternRecognition.domain.Point;
import com.rmeloni.PatternRecognition.persistence.memDB.MemDBConverter;
import com.rmeloni.PatternRecognition.persistence.memDB.MemDBPoint;
import com.rmeloni.PatternRecognition.persistence.memDB.MemDBRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MemDBRepositoryTest {

    @Autowired
    private MemDBRepository memDBRepository;

    @Test
    void Should_SaveNewPoints_When_InputIsValid() {
        Point p = new Point(3, 4);
        Point q = new Point(4, 5);
        memDBRepository.save(MemDBConverter.fromPoint(p));
        assertEquals(memDBRepository.findAll().iterator().next(), MemDBConverter.fromPoint(p));
        assertEquals(1, ((List<MemDBPoint>) memDBRepository.findAll()).size());
        memDBRepository.save(MemDBConverter.fromPoint(q));
        assertEquals(2, ((List<MemDBPoint>) memDBRepository.findAll()).size());
    }

    @Test
    void ShouldNot_SaveDuplicates() {
        Point p = new Point(3, 4);
        Point q = new Point(3, 4);
        memDBRepository.save(MemDBConverter.fromPoint(p));
        memDBRepository.save(MemDBConverter.fromPoint(q));
        assertEquals(1, ((List<MemDBPoint>) memDBRepository.findAll()).size());
    }

    @Test
    void Should_ClearPoints_When_ClearSpaceIsInvoked() {
        memDBRepository.deleteAll();
        assertEquals(0, ((List<MemDBPoint>) memDBRepository.findAll()).size());
    }
}