package com.simplexx.wnp.baselib.repositories;

import java.util.Collection;
import java.util.List;

/**
 * Created by wnp on 2018/12/18.
 */

public interface IDbRepository<T, K> {
    //key为Long的Repository
    interface LongRepository<T> extends IDbRepository<T, Long> {}
    //key为String的Repository
    interface StringRepository<T> extends IDbRepository<T, String> {}

    /**
     * 一pp次保存单个实体类
     *
     * @param entity
     */
    void save(T entity);

    /**
     * 一次保存多个实体类
     *
     * @param entities
     */
    void save(Iterable<T> entities);

    /**
     * 根据传进来的entity，去删除对应的entity
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * 根据传进来的key，去查找对应的entity，然后进行删除
     *
     * @param key
     */
    void deleteByKey(K key);

    /**
     * 查找实体类
     * 根据key去找找对应的实体
     *
     * @param key
     * @return
     */
    T load(K key);

    /**
     * 查找实体类
     * 一次查找多个实体类
     *
     * @param keys
     * @return
     */
    List<T> load(Collection<K> keys);


    /**
     * 查询是否包含key的数据
     * @param key
     * @return
     */
    boolean exists(K key);
}
