package com.mdziedzic.interview.samplerestservice.cache;

import com.mdziedzic.interview.samplerestservice.model.Person;
import java.util.ArrayList;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.map.LRUMap;

@Log4j2
public final class MemoryCache {

    private final LRUMap<Long, CachedPersonWrapper> cacheMap;
    private final long liveTime;

    public MemoryCache(int maxItemsSize, long liveTime, long timerInterval) {
        this.liveTime = liveTime;
        cacheMap = new LRUMap<>(maxItemsSize);
        var thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(timerInterval);
                } catch (InterruptedException ex) {
                    log.info(String.format("Failed to cleanUp: %s", ex.getMessage()));
                }
                cleanUp();
            }
        }
        );
        thread.setDaemon(true);
        thread.start();
    }

    private void cleanUp() {
        var currentTime = System.currentTimeMillis();
        var keysToDelete = getKeysToDelete(currentTime);
        for (Long key : keysToDelete) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }
            Thread.yield();
        }
    }

    private ArrayList<Long> getKeysToDelete(long currentTime) {
        ArrayList<Long> keysToDelete;
        synchronized (cacheMap) {
            var iterator = cacheMap.mapIterator();
            keysToDelete = new ArrayList<>();
            Long key;
            while (iterator.hasNext()) {
                key = iterator.next();
                CachedPersonWrapper cachedPersonWrapper = iterator.getValue();
                if (isToDelete(currentTime, cachedPersonWrapper)) {
                    keysToDelete.add(key);
                }
            }
        }
        return keysToDelete;
    }

    private boolean isToDelete(long currentTime, CachedPersonWrapper cachedPersonWrapper) {
        return cachedPersonWrapper != null && (currentTime > (liveTime + cachedPersonWrapper.lastAccessedTime));
    }

    public void put(Long key, Person value) {
        synchronized (cacheMap) {
            cacheMap.put(key, new CachedPersonWrapper(value));
        }
    }

    protected int getCacheSize() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    static class CachedPersonWrapper {

        public long lastAccessedTime = System.currentTimeMillis();
        public Person value;

        public CachedPersonWrapper(Person value) {
            this.value = value;
        }
    }
}
