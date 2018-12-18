package com.wnp.lqmall.ioc.module;

import com.simplexx.wnp.repository.INotifyHistoryRepository;
import com.wnp.lqmall.dao.DaoSession;
import com.wnp.lqmall.repository.NotifyHistoryRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wnp on 2018/12/18.
 */

@Module(includes = {DaoSessionModule.class})
public class RepositoryModule {

    @Singleton
    @Provides
    public INotifyHistoryRepository providerNotifyHistoryRepository(DaoSession daoSession) {
        return new NotifyHistoryRepository(daoSession);
    }
}
