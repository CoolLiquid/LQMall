package com.simplexx.wnp.presenter;

import com.simplexx.wnp.baselib.basemvp.IView;
import com.simplexx.wnp.users.AuthorizationService;

import javax.inject.Inject;

/**
 * Created by wnp on 2018/7/2.
 */

public abstract class BasePresenter<T extends IView> extends com.simplexx.wnp.baselib.basemvp.BasePresenter<T> {

    //注入AuthorizationService
    @Inject
    AuthorizationService authorizationService;

    public AuthorizationService authorizationService() {
        return authorizationService;
    }

    public BasePresenter(T iView) {
        super(iView);
    }
}
