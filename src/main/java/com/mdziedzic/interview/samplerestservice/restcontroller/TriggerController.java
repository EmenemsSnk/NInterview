package com.mdziedzic.interview.samplerestservice.restcontroller;

import com.mdziedzic.interview.samplerestservice.jms.JmsPublisher;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("trigger")
public class TriggerController {

    private static final String MESSAGE = "triggerJob";
    private final JmsPublisher jmsPublisher;

    @GetMapping
    public void sendTriggerMessage() {
        jmsPublisher.publish(MESSAGE);
    }
}

