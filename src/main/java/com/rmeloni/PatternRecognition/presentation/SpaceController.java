package com.rmeloni.PatternRecognition.presentation;

import com.rmeloni.PatternRecognition.domain.Point;
import com.rmeloni.PatternRecognition.domain.Segment;
import com.rmeloni.PatternRecognition.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping(path = "/space", produces = "application/json")
    private HashSet<Point> getPoints() {
        return spaceService.getPoints();
    }

    @GetMapping(path = "/lines/{n}", produces = "application/json")
    private List<Segment> getLines(@PathVariable int n) {   // TODO validare n
        System.err.println("n: " + n);
        return new ArrayList<>();
    }

    @PostMapping(path = "/point", produces = "application/json", consumes = "application/json")
    private Point addPoint(@Valid @RequestBody Point p) {
        return spaceService.addPoint(p);
    }

    @DeleteMapping(path = "/delete")
    private HashSet<Point> deletePoints() {
        spaceService.clearSpace();
        return spaceService.getPoints();
    }

}
