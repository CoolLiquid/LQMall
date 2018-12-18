package com.wnp.lqmall.ioc.module;

import com.wnp.lqmall.dao.DaoSession;

import org.greenrobot.greendao.annotation.NotNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wnp on 2018/12/18.
 */

@Module
public class DaoSessionModule {
    DaoSession daoSession;

    public DaoSessionModule(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Singleton
    @Provides
    public DaoSession providerDaoSession() {
        return this.daoSession;
    }
}
