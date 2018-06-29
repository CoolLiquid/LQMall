package com.simplexx.wnp.baselib.repositories;


/**
 * Created by weinp on 2017/4/20.
 */
public interface IWriteableSingleRepository<T> {
    void addOrReplace(T value);

    void clear();
}
