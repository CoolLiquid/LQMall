package com.simplexx.wnp.util.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.simplexx.wnp.baselib.basemvp.BasePresenter;
import com.simplexx.wnp.baselib.basemvp.IView;
import com.simplexx.wnp.util.PresenterUtil;
import com.simplexx.wnp.util.R;
import com.simplexx.wnp.util.ui.StatusBarUtil;

/**
 * Created by wnp on 2018/6/25.
 */

public abstract class PresenterActivity<T extends BasePresenter<E>, E extends IView> extends BaseActivity {
    private T presenter;

    protected T getPresenter() {
        return presenter;
    }

    protected T createPresenter() {
        presenter = PresenterUtil.createPresenter(this);
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!canRotateScreen())
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        createPresenter();
        initView(savedInstanceState);
        presenter.onViewCreate();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    /**
     * 需要statusBar特别的定制，可以在子类中重写该方法
     */
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
    }

    protected boolean canRotateScreen() {
        return false;
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
