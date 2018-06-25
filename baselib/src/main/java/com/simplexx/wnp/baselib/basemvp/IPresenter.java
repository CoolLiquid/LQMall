package com.simplexx.wnp.baselib.basemvp;

public interface IPresenter<T extends IView> {
    T getView();

    void onViewCreate();

    void onViewDestroy();
}