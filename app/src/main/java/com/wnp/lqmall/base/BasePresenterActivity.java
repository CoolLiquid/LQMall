package com.wnp.lqmall.base;

import com.simplexx.wnp.baselib.basemvp.BasePresenter;
import com.simplexx.wnp.baselib.basemvp.IView;
import com.simplexx.wnp.util.base.PresenterActivity;

/**
 * Created by wnp on 2018/6/26.
 */

public abstract class BasePresenterActivity<T extends BasePresenter<E>, E extends IView>
        extends PresenterActivity<T, E> {


    @Override
    protected T createPresenter() {
        T presenter = super.createPresenter();
        //使用dagger初始化presenter的成员变量

        return presenter;
    }

    @Override
    protected boolean canRotateScreen() {
        return false;
    }
}
