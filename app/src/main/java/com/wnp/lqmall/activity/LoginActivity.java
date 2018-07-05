package com.wnp.lqmall.activity;

import android.os.Bundle;

import com.simplexx.wnp.presenter.LoginPresenter;
import com.wnp.lqmall.R;
import com.wnp.lqmall.base.BasePresenterActivity;
import com.wnp.lqmall.ioc.component.PresenterComponent;

/**
 * Created by wnp on 2018/7/2.
 */

public class LoginActivity extends BasePresenterActivity<LoginPresenter, LoginPresenter.ILoginView>
        implements LoginPresenter.ILoginView {

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        getPresenter().request();
    }

    @Override
    protected void injectPresenter(PresenterComponent component, LoginPresenter preseneter) {
        component.inject(preseneter);
    }


    @Override
    protected void onForceLogout() {

    }

    @Override
    protected void onTokenOutDate() {

    }
}
