package com.wnp.lqmall.base;

import com.simplexx.wnp.baselib.basemvp.BasePresenter;
import com.simplexx.wnp.baselib.basemvp.IView;
import com.simplexx.wnp.baselib.exception.NetWorkException;
import com.simplexx.wnp.baselib.executor.ActionRequest;
import com.simplexx.wnp.util.base.PresenterFragment;
import com.wnp.lqmall.ioc.component.DaggerPresenterComponent;
import com.wnp.lqmall.ioc.component.PresenterComponent;

/**
 * Created by wnp on 2018/7/3.
 */

public abstract class BasePresenterFragment<T extends BasePresenter<E>, E extends IView>
        extends PresenterFragment<T, E> {

    @Override
    public T createPresenter() {
        T presenter = super.createPresenter();
        PresenterComponent component = DaggerPresenterComponent.builder().build();
        inject(component, presenter);
        return presenter;
    }

    @Override
    public void onException(ActionRequest request, NetWorkException e) {
        super.onException(request, e);
        if (this.isAdded()) {
            // TODO: 2018/7/3 actionReloadDialogFragment__show
        }
    }

    protected abstract void inject(PresenterComponent component, T presenter);
}
