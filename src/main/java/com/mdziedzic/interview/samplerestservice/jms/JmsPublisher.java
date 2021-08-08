package com.mdziedzic.interview.samplerestservice.jms;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class JmsPublisher {

    private final JmsTemplate jmsTemplate;
    @Value("${activemq.destination}")
    private String destination;

    @Autowired
    public JmsPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void publish(String msg) {
        jmsTemplate.convertAndSend(destination, msg);
        log.debug("Message sent");
    }
}
