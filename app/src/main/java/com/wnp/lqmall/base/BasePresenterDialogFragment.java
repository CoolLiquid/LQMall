package com.wnp.lqmall.base;

import com.simplexx.wnp.baselib.basemvp.BasePresenter;
import com.simplexx.wnp.baselib.basemvp.IView;
import com.simplexx.wnp.baselib.exception.NetWorkException;
import com.simplexx.wnp.baselib.executor.ActionRequest;
import com.simplexx.wnp.util.base.PresenterDialogFragment;
import com.wnp.lqmall.ioc.component.DaggerPresenterComponent;
import com.wnp.lqmall.ioc.component.PresenterComponent;

/**
 * Created by wnp on 2018/7/3.
 */

public abstract class BasePresenterDialogFragment<T extends BasePresenter<E>, E extends IView>
        extends PresenterDialogFragment<T, E> {

    @Override
    protected T createPresenter() {
        T presenter = super.createPresenter();
        PresenterComponent component = DaggerPresenterComponent.builder().build();
        inject(component, presenter);
        return presenter;
    }

    public abstract void inject(PresenterComponent component, T presenter);

    @Override
    public void onException(ActionRequest request, NetWorkException e) {
        super.onException(request, e);
        if (this.isAdded()) {
            // TODO: 2018/7/3 show ActionRequestDialog__show 
        }
    }

    @Override
    public void dismiss() {
        hideKeyBoard();
        super.dismiss();
    }
}
