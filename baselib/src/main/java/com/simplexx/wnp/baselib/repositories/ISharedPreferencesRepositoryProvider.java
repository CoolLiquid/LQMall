package com.simplexx.wnp.baselib.repositories;

/**
 * Created by wnp on 2018/6/26.
 */

public interface ISharedPreferencesRepositoryProvider {
    <T> ISingleRepository<T> GetSingleRepository(String sharedPreferencesName, String key, Class<T> classOfT);

    IWriteableSingleRepository GetWriteableSingleRepository(String sharedPreferencesName, String key);
}
