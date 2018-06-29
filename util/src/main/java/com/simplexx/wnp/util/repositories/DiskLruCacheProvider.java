package com.simplexx.wnp.util.repositories;


import com.jakewharton.disklrucache.DiskLruCache;
import com.simplexx.wnp.baselib.repositories.CacheStore;
import com.simplexx.wnp.baselib.repositories.CacheStoreProvider;
import com.simplexx.wnp.baselib.util.StringUtil;
import com.simplexx.wnp.util.FileHelper;

import java.io.File;
import java.io.IOException;

/**
 * Created by fan-gk on 2017/4/24.
 */


public class DiskLruCacheProvider extends CacheStoreProvider {

    private class DiskLruCacheStore extends CacheStore {
        private static final int APP_VERSION = 1;
        private static final int INDEX_VALUE = 0;
        private static final int INDEX_TIME = 1;
        private final DiskLruCache diskLruCache;

        public DiskLruCacheStore(String directory, int maxSize) throws IOException {
            diskLruCache = DiskLruCache.open(new File(FileHelper.getCacheDir(), directory), APP_VERSION, 2, maxSize);
        }

        @Override
        protected void realSet(String key, CacheData data) {
            try {
                DiskLruCache.Editor editor = diskLruCache.edit(key);
                editor.set(INDEX_VALUE, data.value == null ? StringUtil.EMPTY : data.value);
                editor.set(INDEX_TIME, data.millisTime == null ? StringUtil.EMPTY : String.valueOf(data.millisTime.longValue()));
                editor.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected CacheData realGet(String key) {
            try {
                DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
                if(snapshot != null){
                    String timeString = snapshot.getString(INDEX_TIME);
                    Long time = StringUtil.isNullOrEmpty(timeString) ? null : Long.parseLong(timeString);
                    String value = snapshot.getString(INDEX_VALUE);
                    return new CacheData(time, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void realDel(String key) {
            try {
                diskLruCache.remove(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private final String directory;
    private final int maxSize;
    private DiskLruCacheStore store = null;

    public DiskLruCacheProvider(String directory, int maxSize){
        this.directory = directory;
        this.maxSize = maxSize;
    }

    @Override
    public synchronized CacheStore getCacheStore() {
        if(store == null){
            try {
                store = new DiskLruCacheStore(directory, maxSize);
            } catch (Exception e) {
                e.printStackTrace();
                return CacheStore.None;
            }
        }
        return store;
    }
}

