package com.yarosh.checks.service.util.cache;

import com.yarosh.checks.domain.Check;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetCheckCacheEventLogger implements CacheEventListener<Long, Check> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetCheckCacheEventLogger.class);

    @Override
    public void onEvent(CacheEvent<? extends Long, ? extends Check> cacheEvent) {
        LOGGER.debug("Get check cache event, old value: {}, new value: {}", cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }
}
