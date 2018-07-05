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
     * �ײ�get�����׳��쳣�������쳣ʹ��
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
     * ���Ȼ�ȡ���棬���������ڻ�ȡGetter��Getterʧ�ܵĻ��᷵�ػ���
     *
     * @return ��������Getter��ʧ�ܣ�����null
     */
    public T get() {
        return get(true);
    }

    /**
     * ����preferCache���Ի�ȡ����
     *
     * @param preferCache ���Ϊfalse���Ȼ�ȡGetter�����Getter��ȡʧ�ܷ��ػ���
     * @return ��������Getter��ʧ�ܣ�����null
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

