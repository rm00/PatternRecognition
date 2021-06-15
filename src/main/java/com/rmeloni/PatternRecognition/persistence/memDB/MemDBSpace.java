package com.rmeloni.PatternRecognition.persistence.memDB;

import com.rmeloni.PatternRecognition.domain.Point;
import com.rmeloni.PatternRecognition.persistence.SpaceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MemDBSpace implements SpaceDao {

    @Autowired
    private MemDBRepository memDBRepository;

    @Override
    public Point addPoint(Point p) {
        MemDBPoint memDBPoint = memDBRepository.save(MemDBConverter.fromPoint(p));
        return MemDBConverter.toPoint(memDBPoint);
    }

    @Override
    public Set<Point> getPoints() {
        List<MemDBPoint> memDBPointList = (List<MemDBPoint>) memDBRepository.findAll();
        return memDBPointList.stream()
                .map(MemDBConverter::toPoint)
                .collect(Collectors.toSet());
    }

    @Override
    public void clearSpace() {
        memDBRepository.deleteAll();
    }

}
