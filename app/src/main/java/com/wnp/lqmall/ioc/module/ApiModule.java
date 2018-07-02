package com.wnp.lqmall.ioc.module;

import com.simplexx.wnp.presenter.resource.api.ILoginService;
import com.wnp.lqmall.http.HttpApiProxyCreate;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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

    @Provides
    public ILoginService providerLoginService() {
       return creater.create(ILoginService.class);
    }
}
