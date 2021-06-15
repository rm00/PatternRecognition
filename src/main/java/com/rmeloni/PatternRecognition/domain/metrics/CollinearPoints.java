package com.rmeloni.PatternRecognition.domain.metrics;

import com.rmeloni.PatternRecognition.domain.Segment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CollinearPoints {
    List<Segment> getListOfSegmentsWithAtLeastNCollinearPoints(Integer n);
}
