package com.rmeloni.PatternRecognition.domain.events;

import com.rmeloni.PatternRecognition.domain.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PointAddedEventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(PointAddedEventPublisher.class);
    private static final String MESSAGE = "Event published: added new Point";

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish(Point p) {
        applicationEventPublisher.publishEvent(new PointAddedEvent(p));
        logger.info(MESSAGE + " " + p.toString());
    }
}
