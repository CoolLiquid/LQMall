package com.simplexx.wnp.util.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simplexx.wnp.baselib.basemvp.BasePresenter;
import com.simplexx.wnp.baselib.basemvp.IView;
import com.simplexx.wnp.util.PresenterUtil;

/**
 * Created by wnp on 2018/7/3.
 */

public class PresenterDialogFragment<T extends BasePresenter<E>, E extends IView> extends BaseDialogFragment {
    T presenter;

    protected T createPresenter() {
        presenter = PresenterUtil.createPresenter(this);
        return presenter;
    }

    public T getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        presenter.onViewCreate();
        return view;
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
    public void onStop() {
        super.onStop();
        presenter.onViewStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroy();
    }
}
