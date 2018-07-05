package com.simplexx.wnp.baselib.repositories;


import com.simplexx.wnp.baselib.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * Created by fan-gk on 2017/4/24.
 */


public final class CacheGetter<T> {

    public interface IGetter<T> {
        T get() throws Exception;
    }

    private final Class<T> classOfT;
    private final Type typeOfT;
    private final CacheStoreProvider provider;
    private final String key;
    private final IGetter<T> getter;
    private final Long millisTime;

    public CacheGetter(Class<T> classOfT, Type typeOfT, CacheStoreProvider provider, String key, IGetter<T> getter, Long millisTime) {
        this.classOfT = classOfT;
        this.typeOfT = typeOfT;
        this.provider = provider;
        this.key = key;
        this.getter = getter;
        this.millisTime = millisTime;
    }

    /**
     * 底层get，会抛出异常，不抛异常使用
     *
     * @param preferCache
     * @return
     * @throws Exception
     * @see CacheGetter#get(boolean)
     */
    public T realGet(boolean preferCache) throws Exception {
        CacheStore store = provider.getCacheStore();
        CacheStore.CacheData data = getCache(store);
        T value = null;
        if (preferCache && data != null && !data.isExpired())
            value = parseCacheValue(data.value);
        if (value == null)
            try {
                value = getter.get();
                setCache(value, store);
            } catch (Exception e) {
                throw e;
            }
        if (value == null && data != null)
            value = parseCacheValue(data.value);
        return value;
    }

    /**
     * 优先获取缓存，如果缓存过期获取Getter，Getter失败的话会返回缓存
     *
     * @return 如果缓存和Getter都失败，返回null
     */
    public T get() {
        return get(true);
    }

    /**
     * 根据preferCache策略获取缓存
     *
     * @param preferCache 如果为false，先获取Getter，如果Getter获取失败返回缓存
     * @return 如果缓存和Getter都失败，返回null
     */

    public T get(boolean preferCache) {
        try {
            return realGet(preferCache);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public T getCacheOnly(boolean allowExpired) {
        CacheStore store = provider.getCacheStore();
        CacheStore.CacheData data = getCache(store);
        if (data == null)
            return null;
        if (!allowExpired && data.isExpired())
            return null;
        return parseCacheValue(data.value);
    }

    public T getNewest() throws Exception {
        T value = getter.get();
        CacheStore store = provider.getCacheStore();
        setCache(value, store);
        return value;
    }

    private CacheStore.CacheData getCache(CacheStore store) {
        if (store == null)
            return null;
        return store.realGet(key);
    }

    private T parseCacheValue(String value) {
        if (typeOfT != null)
            return GsonUtils.tryParse(typeOfT, value);
        if (classOfT != null)
            return GsonUtils.tryParse(classOfT, value);
        return null;
    }

    private void setCache(T value, CacheStore store) {
        if (store != null && value != null) {
            if (millisTime == null)
                store.set(key, value);
            else
                store.set(key, value, millisTime.longValue(), TimeUnit.MILLISECONDS);
        }
    }


    public void delCache(String key) {
        CacheStore store = provider.getCacheStore();
        store.del(key);
    }


}

