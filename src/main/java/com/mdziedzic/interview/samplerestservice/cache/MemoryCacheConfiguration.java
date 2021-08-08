package com.mdziedzic.interview.samplerestservice.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryCacheConfiguration {

    @Value("${memoryCache.maxItemsSize}")
    private int maxItemsSize;
    @Value("${memoryCache.liveTime}")
    private long liveTime;
    @Value("${memoryCache.timerInterval}")
    private long timerInterval;

    @Bean
    public MemoryCache memoryCache() {
        return new MemoryCache(maxItemsSize, liveTime, timerInterval);
    }

}
