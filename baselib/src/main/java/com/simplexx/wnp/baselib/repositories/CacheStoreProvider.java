package com.simplexx.wnp.baselib.repositories;

import java.lang.reflect.Type;

/**
 * Created by fan-gk on 2017/4/24.
 */

public abstract class CacheStoreProvider {
    public abstract CacheStore getCacheStore();

    public <T> CacheGetter<T> createGetter (String key, Class<T> classOfT, CacheGetter.IGetter<T> getter, long millisTime) {
        return new CacheGetter(classOfT, null, this, key, getter, millisTime);
    }

    public <T> CacheGetter<T> createGetter(String key, Type typeOfT, CacheGetter.IGetter<T> getter, long millisTime){
        return new CacheGetter(null, typeOfT, this, key, getter, millisTime);
    }

    public <T> CacheGetter<T> createGetter(String key, Class<T> classOfT, CacheGetter.IGetter<T> getter){
        return new CacheGetter(classOfT, null, this, key, getter, null);
    }

    public <T> CacheGetter<T> createGetter(String key, Type typeOfT, CacheGetter.IGetter<T> getter){
        return new CacheGetter(null, typeOfT, this, key, getter, null);
    }
}
