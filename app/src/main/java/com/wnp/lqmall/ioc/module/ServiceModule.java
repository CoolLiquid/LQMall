package com.wnp.lqmall.ioc.module;

import com.simplexx.wnp.baselib.repositories.CacheStoreProvider;
import com.simplexx.wnp.baselib.ui.ClientSettings;
import com.simplexx.wnp.users.AuthorizationService;
import com.simplexx.wnp.users.UserFactory;
import com.simplexx.wnp.util.repositories.DiskLruCacheProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wnp on 2018/6/26.
 */
@Module(includes = {ApiModule.class})
public class ServiceModule {
    @Provides
    @Singleton
    public CacheStoreProvider provideCacheStoreProvider() {
        // TODO: 2018/6/26 可以自定义cacheProvider的存储路径
        return new DiskLruCacheProvider("lqmall-frame", 50 * 1024 * 1024);//50M
    }

    @Provides
    public ClientSettings provideClientSettings() {
        //底层实现是sharePreference
        return new ClientSettings();
    }

    @Provides
    public AuthorizationService authorizationService(ClientSettings clientSettings, UserFactory userFactory) {
        return new AuthorizationService(clientSettings, userFactory);
    }

    @Provides
    public UserFactory provideUserFactory() {
        return new UserFactory();
    }

}
