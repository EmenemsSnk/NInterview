package com.mdziedzic.interview.samplerestservice.cache;

import com.mdziedzic.interview.samplerestservice.SampleRestServiceApplication;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest(classes = SampleRestServiceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@TestInstance(PER_CLASS)
class RestServiceIT {

    @Autowired
    private MemoryCache memoryCache;
    @LocalServerPort
    private int randomServerPort;
    private RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
    }

    @Test
    void jobShouldAddDataToCacheWhenTriggered() throws URISyntaxException, InterruptedException {
        //given
        var expectedResponseCode = 200;
        var baseUrl = String.format("http://localhost:%s/trigger", randomServerPort);
        var uri = new URI(baseUrl);
        //when
        var result = restTemplate.getForEntity(uri, String.class);
        //then
        assertEquals(expectedResponseCode, result.getStatusCodeValue());
        Thread.sleep(2000);
        assertTrue(memoryCache.getCacheSize() > 0);
    }
}

