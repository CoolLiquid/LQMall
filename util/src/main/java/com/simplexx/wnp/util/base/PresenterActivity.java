package com.simplexx.wnp.util.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.simplexx.wnp.baselib.basemvp.BasePresenter;
import com.simplexx.wnp.baselib.basemvp.IView;
import com.simplexx.wnp.util.PresenterUtil;

/**
 * Created by wnp on 2018/6/25.
 */

public abstract class PresenterActivity<T extends BasePresenter, E extends IView> extends BaseActivity {
    private T presenter;

    protected T getPresenter() {
        return presenter;
    }

    protected T createPresenter() {
        presenter = PresenterUtil.createPresenter(this);
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if (!canRotateScreen())
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        createPresenter();
        initView(savedInstanceState);
        presenter.onViewCreate();
    }

    protected boolean canRotateScreen() {
        return true;
    }

    protected abstract void initView(Bundle savedInstanceState);

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroy();
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        presenter.onViewResume();
    }

    @Override
    @CallSuper
    protected void onPause() {
        super.onPause();
        presenter.onViewPause();
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        presenter.onViewStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onViewStop();
    }
}
