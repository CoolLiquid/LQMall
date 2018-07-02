package com.wnp.lqmall.ioc.component;

import com.simplexx.wnp.presenter.LoginPresenter;
import com.wnp.lqmall.ioc.module.ApiModule;
import com.wnp.lqmall.ioc.module.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by wnp on 2018/6/26.
 */
@Singleton
@Component(modules = {ServiceModule.class})
public interface PresenterComponent {

    void inject(LoginPresenter presenter);

}
