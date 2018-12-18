package com.wnp.lqmall.repository;

import com.simplexx.wnp.baselib.repositories.IDbRepository;
import com.wnp.lqmall.dao.DaoSession;

import org.greenrobot.greendao.AbstractDao;

import java.util.Collection;
import java.util.List;

/**
 * Created by wnp on 2018/12/18.
 */

public abstract class BaseRepoditory<D extends AbstractDao<T, K>, T, K> implements IDbRepository<T, K> {

    private DaoSession daoSession;

    public BaseRepoditory(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    protected D internalGetDao() {
        return getDao(daoSession);
    }

    protected abstract D getDao(DaoSession daoSession);

    @Override
    public void save(T entity) {
        internalGetDao().insertOrReplace(entity);
    }

    @Override
    public void save(Iterable<T> entities) {
        internalGetDao().insertOrReplaceInTx(entities);
    }

    @Override
    public void delete(T entity) {
        internalGetDao().delete(entity);
    }

    @Override
    public void deleteByKey(K key) {
        internalGetDao().deleteByKey(key);
    }

    @Override
    public T load(K key) {
        return internalGetDao().load(key);
    }

    @Override
    public List<T> load(Collection<K> keys) {
        return internalGetDao().queryBuilder().where(internalGetDao().getPkProperty().in(keys)).list();
    }

    @Override
    public boolean exists(K key) {
        return internalGetDao().queryBuilder().where(internalGetDao().getPkProperty().eq(key)).count() > 0;
    }
}
