package com.simplexx.wnp.util.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.simplexx.wnp.baselib.basemvp.BasePresenter;
import com.simplexx.wnp.baselib.basemvp.IView;
import com.simplexx.wnp.util.PresenterUtil;

/**
 * Created by wnp on 2018/7/3.
 */

public abstract class PresenterFragment<T extends BasePresenter<E>, E extends IView> extends BaseFragment {

    T presenter;

    public T createPresenter() {
        presenter = PresenterUtil.createPresenter(this);
        return presenter;
    }

    public T getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onViewCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onViewStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onViewPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onViewStop();
    }
}
