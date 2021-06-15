package com.rmeloni.PatternRecognition.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmeloni.PatternRecognition.domain.Point;
import com.rmeloni.PatternRecognition.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(path = "/space", produces = "application/json")
    private Set<Point> getPoints() {
        return spaceService.getPoints();
    }

    @GetMapping(path = "/lines/{n}", produces = "application/json")
    private ResponseEntity<Object> getListOfSegments(@PathVariable Integer n) {
        if (n < 2) {
            Map<String, String> body = new HashMap<>() {{
                put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
                put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
                put("message", "n must be >=2");
            }};
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(spaceService.getListOfSegmentsWithAtLeastNCollinearPoints(n));
        }
    }

    @PostMapping(path = "/point", produces = "application/json", consumes = "application/json")
    private Point addPoint(@RequestBody @Valid Point p) {
        return spaceService.addPoint(p);
    }

    @DeleteMapping(path = "/delete")
    private Set<Point> clearSpace() {
        spaceService.clearSpace();
        return spaceService.getPoints();
    }

}
