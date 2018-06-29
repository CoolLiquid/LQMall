package com.wnp.lqmall.ioc.component;

import com.wnp.lqmall.ioc.module.ApiModule;
import com.wnp.lqmall.ioc.module.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by wnp on 2018/6/26.
 */
@Singleton
@Component(modules = {ApiModule.class, ServiceModule.class})
public class PresenterComponent {

}
