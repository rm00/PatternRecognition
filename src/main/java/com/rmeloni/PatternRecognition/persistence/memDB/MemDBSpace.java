package com.rmeloni.PatternRecognition.persistence.memDB;

import com.rmeloni.PatternRecognition.domain.Point;
import com.rmeloni.PatternRecognition.domain.SpaceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class MemDBSpace implements SpaceDao {

    @Autowired
    private MemDBRepository repository;

    @Override
    public Point addPoint(Point p) {
        MemDBPoint memDBPoint = repository.save(MemDBConverter.fromPoint(p));
        return MemDBConverter.toPoint(memDBPoint);
    }

    @Override
    public HashSet<Point> getPoints() {
        HashSet<MemDBPoint> memDBPointHashSet = (HashSet<MemDBPoint>) repository.findAll();
        return (HashSet<Point>) memDBPointHashSet.stream()
                .map(MemDBConverter::toPoint)
                .collect(Collectors.toSet());
    }

    @Override
    public void clearSpace() {
        repository.deleteAll();
    }

}
