package com.mdziedzic.interview.samplerestservice.cache;

import com.mdziedzic.interview.samplerestservice.model.Person;
import java.sql.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemoryCacheTest {

    private static final int MAX_ITEMS_SIZE = 10;
    private static final int LIVE_TIME = 100;
    private static final int TIMER_INTERVAL = 100;
    private static final int DUMMY_DATA_INITIAL_SIZE = 3;
    private List<Person> dummyData;
    private MemoryCache cut;

    @BeforeEach
    void setup() {
        cut = new MemoryCache(MAX_ITEMS_SIZE, LIVE_TIME, TIMER_INTERVAL);
        dummyData = createDummyData();
    }

    @Test
    void cacheShouldBeCleanedAfterSpecifiedTime() throws InterruptedException {
        //given
        dummyData.forEach(person -> cut.put(person.getId(), person));
        //when-then
        assertEquals(DUMMY_DATA_INITIAL_SIZE, cut.getCacheSize());
        Thread.sleep(1000);
        assertEquals(0, cut.getCacheSize());
    }

    @Test
    void cacheShouldBeStillInCacheAfterSpecifiedTime() throws InterruptedException {
        //given
        dummyData.forEach(person -> cut.put(person.getId(), person));
        //when-then
        assertEquals(DUMMY_DATA_INITIAL_SIZE, cut.getCacheSize());
        Thread.sleep(TIMER_INTERVAL / 2);
        assertEquals(DUMMY_DATA_INITIAL_SIZE, cut.getCacheSize());
    }

    private List<Person> createDummyData() {
        return List.of(new Person(1L, "email", "name", "lastName", new Date(1)),
            new Person(2L, "email", "name", "lastName", new Date(1)),
            new Person(3L, "email", "name", "lastName", new Date(1))
        );
    }
}



