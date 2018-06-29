package com.simplexx.wnp.baselib.repositories;


import com.simplexx.wnp.baselib.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * Created by fan-gk on 2017/4/24.
 */


public abstract class CacheStore {
    protected class CacheData{
        public final Long millisTime;
        public final String value;

        public CacheData(Long millisTime, String value) {
            this.millisTime = millisTime;
            this.value = value;
        }

        public boolean isExpired(){
            return millisTime != null && millisTime.longValue() < System.currentTimeMillis();
        }
    }

    public static final CacheStore None = new CacheStore() {
        @Override
        protected void realSet(String key, CacheData data) {
            return;
        }

        @Override
        protected CacheData realGet(String key) {
            return null;
        }

        @Override
        protected void realDel(String key) {
            return;
        }
    };

    /**
     * 以json方式存储
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     * @param <T>
     */
    public final <T> void set(String key, T value, long time, TimeUnit timeUnit){
        String json = GsonUtils.stringify(value);
        realSet(key, new CacheData(timeUnit.toMillis(time) + System.currentTimeMillis(), json));
    }
    /**
     * 以json方式存储
     * @param key
     * @param value
     * @param <T>
     */
    public final <T> void set(String key, T value){
        String json = GsonUtils.stringify(value);
        realSet(key, new CacheData(null, json));
    }

    /**
     * 获取以json存储的值
     * @param key
     * @param classOfT
     * @param <T>
     * @return
     */
    public final <T> T get(String key, Class<T> classOfT){
        CacheData json = realGet(key);
        if(json == null || json.isExpired())
            return null;
        T value = GsonUtils.tryParse(classOfT, json.value);
        return value;
    }

    /**
     * 获取以json存储的值
     * @param key
     * @param typeOfT
     * @param <T>
     * @return
     */
    public final <T> T get(String key, Type typeOfT){
        CacheData json = realGet(key);
        if(json == null || json.isExpired())
            return null;
        T value = GsonUtils.tryParse(typeOfT, json.value);
        return value;
    }

    /**
     * 删除缓存
     * @param key
     */
    public final void del(String key){
        realDel(key);
    }

    protected abstract void realSet(String key, CacheData data);
    protected abstract CacheData realGet(String key);
    protected abstract void realDel(String key);
}

