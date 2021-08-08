package com.mdziedzic.interview.samplerestservice.restcontroller;

import com.mdziedzic.interview.samplerestservice.jms.JmsPublisher;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

//@ExtendWith(MockitoExtension.class)
class TriggerControllerTest {

    @InjectMocks
    TriggerController cut;
    @Mock
    private JmsPublisher jmsPublisher;

    @Test
    void whenCallTriggerEndpointShouldPublishMessage() {
        Map<String, String> n;// = new HashMap<>();
n.isEmpty();
        MockitoAnnotations.openMocks(this);
        //given
        doNothing().when(jmsPublisher).publish(anyString());
        //when
        cut.sendTriggerMessage();
        //then
        verify(jmsPublisher).publish(anyString());
    }

}
