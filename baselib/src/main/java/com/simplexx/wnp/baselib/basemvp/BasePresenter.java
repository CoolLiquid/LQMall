package com.simplexx.wnp.baselib.basemvp;

/**
 * Created by wnp on 2018/6/25.
 */

public class BasePresenter<T extends IView> implements IPresenter<T> {

    private T iView;

    public BasePresenter(T iView) {
        this.iView = iView;
    }

    @Override
    public T getView() {
        return iView;
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewDestroy() {

    }

    /**
     * run in mian thread
     */
    public void onViewResume() {
    }

    /**
     * run in mian thread
     */
    public void onViewPause() {
    }

    /**
     * run in mian thread
     */
    public void onViewStart() {
    }

    /**
     * run in mian thread
     */
    public void onViewStop() {
    }
}
