package com.wnp.lqmall.ioc.module;

import com.wnp.lqmall.http.HttpApiProxyCreate;

import javax.inject.Singleton;

import dagger.Module;

/**
 * Created by wnp on 2018/6/26.
 */
@Singleton
@Module
public class ApiModule {
    private HttpApiProxyCreate creater;

    public ApiModule() {
        this.creater = new HttpApiProxyCreate();
    }
}
