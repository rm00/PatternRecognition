package com.rmeloni.PatternRecognition.domain.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpaceClearedEventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(SpaceClearedEventPublisher.class);
    private static final String MESSAGE = "Event published: Space cleared";

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish() {
        applicationEventPublisher.publishEvent(new SpaceClearedEvent());
        logger.info(MESSAGE);
    }
}
